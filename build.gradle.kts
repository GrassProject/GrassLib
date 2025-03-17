plugins {
    kotlin("jvm") version "2.1.10"
    id("com.gradleup.shadow") version "9.0.0-beta10"
    id("maven-publish")
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