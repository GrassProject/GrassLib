package com.github.grassproject.grassLib.fakeEntity

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

@Serializable
data class SkinProfile(
    val id: String,
    val name: String,
    val properties: List<Property>
){
    @Serializable
    data class Property(
        val name: String,
        val value: String,
        val signature: String? = null
    )

    fun getSkinTexture(): SkinTexture? {
        return properties.find { it.name == "textures" }?.value?.let {
            val decodedString = Base64.getDecoder().decode(it).decodeToString()
            Json { ignoreUnknownKeys = true }.decodeFromString<SkinTexture>(decodedString)
        }
    }

    @Serializable
    data class SkinTexture(
        val textures: Textures
    )

    @Serializable
    data class Textures(
        val SKIN: SkinDetails
    )

    @Serializable
    data class SkinDetails(val url: String)
}

class MojangAPI{
    suspend fun fetchSkinProfile(username: String): SkinProfile? {
        return withContext(Dispatchers.IO) {
            val client = HttpClient.newHttpClient()
            val url = "https://api.mojang.com/users/profiles/minecraft/$username"
            val request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            val profile = response.body()

            val uuid = Json.decodeFromString<SkinProfile>(profile).id
            fetchSkinProfileByUUID(uuid)
        }
    }

    suspend fun fetchSkinProfileByUUID(uuid: String): SkinProfile? {
        return withContext(Dispatchers.IO) {
            val client = HttpClient.newHttpClient()
            val url = "https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false"
            val request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            val profile = response.body()

            Json.decodeFromString<SkinProfile>(profile)
        }
    }
}