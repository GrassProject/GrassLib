package com.github.grassproject.grassLib.entity

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.wrappers.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType
import java.util.*

class VirtualEntity(
    private val location: Location,
    private val entityType: EntityType = EntityType.PLAYER,
    private var displayName: String = "NPC",
    private var skinName: String = "Notch",
    private var yaw: Float = 0f,
    private var pitch: Float = 0f
) {
    private val protocolManager: ProtocolManager = ProtocolLibrary.getProtocolManager()
    private val entityId: Int = (0..999999).random()
    private val uuid: UUID = UUID.randomUUID()

    suspend fun spawnNPC() {
        try {

            val skinProfile = withContext(Dispatchers.IO) { MojangAPI().fetchSkinProfile(skinName) } ?: return

            val gameProfile = WrappedGameProfile(uuid, displayName)
            skinProfile.properties.forEach { prop ->
                gameProfile.properties.put(prop.name, WrappedSignedProperty(prop.name, prop.value, prop.signature))
            }

            val playerInfoPacket = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO)
            playerInfoPacket.playerInfoAction.write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER)
            playerInfoPacket.playerInfoDataLists.write(
                0, listOf(
                    PlayerInfoData(
                        gameProfile,
                        0,
                        EnumWrappers.NativeGameMode.SURVIVAL,
                        WrappedChatComponent.fromText("§e$displayName")
                    )
                )
            )

            val spawnPacket = protocolManager.createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN)
            spawnPacket.integers.write(0, entityId)
            spawnPacket.uuiDs.write(0, uuid)
            spawnPacket.doubles
                .write(0, location.x)
                .write(1, location.y)
                .write(2, location.z)
            spawnPacket.bytes
                .write(0, (yaw * 256 / 360).toInt().toByte())
                .write(1, (pitch * 256 / 360).toInt().toByte())

            Bukkit.getOnlinePlayers().forEach { player ->
                protocolManager.sendServerPacket(player, playerInfoPacket)
                protocolManager.sendServerPacket(player, spawnPacket)
            }
        }catch (e: Exception){
            Bukkit.getLogger().warning("${e.message}")
        }
    }
    fun setCustomName(name: String) {
        displayName = name
    }
    fun setSkin(name: String) {
        skinName = name
    }
    fun setRotation(yaw: Float, pitch: Float) {
        this.yaw = yaw
        this.pitch = pitch
    }
}
