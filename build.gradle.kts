import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.0.0-beta10"
    id("maven-publish")
    id("com.vanniktech.maven.publish") version "0.28.0"
}

group = "com.github.grassproject"
version = "1.0"

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.gradleup.shadow")
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") // Paper
        maven("https://mvn.lumine.io/repository/maven-public/") // MythicMob
        maven("https://maven.devs.beer/") // ItemsAdder
        maven("https://repo.nexomc.com/releases") // Nexo
        maven("https://repo.oraxen.com/releases") // Oraxen
        maven("https://nexus.phoenixdevt.fr/repository/maven-public/") // MMOItems - MythicLib
        maven("https://repo.codemc.io/repository/maven-public/") // NBT-API
        maven("https://repo.dmulloy2.net/repository/public/") // protocolLib
        maven("https://jitpack.io") // Vault
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn("shadowJar")
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["java"])
                groupId = groupId
                artifactId = "GrassLib"
                version = version

                pom {
                    name.set("GrassLib")
                    description.set("Library for Minecraft plugin")
                }
            }
        }
    }
}

mavenPublishing {
    coordinates(
        groupId = project.group as String,
        artifactId = project.name,
        version = project.version as String
    )

    pom {
        name.set("GrassLib")
        description.set("Library for Minecraft plugin")
        url.set("https://github.com/GrassProject/GrassLib")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("apo2073")
                name.set("APO2073")
                email.set("apo2073@outlook.com")
            }
            developer {
                id.set("mrjimin")
                name.set("Jimin")
                email.set("aa090402@naver.com")
            }
            developer {
                id.set("wayggstar")
                name.set("Wayggstar")
                email.set("wayggstar@gmail.com")
            }
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    // TODO: make configuration for signing and GPG key
    // https://velog.io/@kshired/%EC%A7%81%EC%A0%91-%EC%A0%9C%EC%9E%91%ED%95%9C-Kotlin-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC%EB%A5%BC-Maven-Central%EC%97%90-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0
} // ./gradlew publishAndReleaseToMavenCentral --no-configuration-cache