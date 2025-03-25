plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    id("com.gradleup.shadow") version "9.0.0-beta10"
}

group = rootProject.group
version = rootProject.version

val exposedVersion: String by project

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.21.1-R0.1-SNAPSHOT") // Paper
    compileOnly("io.lumine","Mythic-Dist", "5.6.1") // MythicMob
    compileOnly("dev.lone","api-itemsadder", "4.0.2-beta-release-11") // ItemsAdder
    compileOnly("io.th0rgal","oraxen","1.189.0") // Oraxen
    compileOnly("com.nexomc", "nexo","1.0.0") // Nexo
    compileOnly("io.lumine", "MythicLib-dist","1.6.2-SNAPSHOT") // MythicLib
    compileOnly("net.Indyuce", "MMOItems-API","6.9.5-SNAPSHOT") // MMOItems
    compileOnly("com.arcaniax","HeadDatabase-API", "1.3.2") // HeadDatabase
    compileOnly("com.comphenix.protocol","ProtocolLib","5.3.0") // ProtocolLib
    compileOnly("com.github.MilkBowl", "VaultAPI","1.7.1") // Vault
    compileOnly("su.nightexpress.coinsengine", "CoinsEngine","2.4.1") // CoinsEngine
    compileOnly("com.sk89q.worldguard", "worldguard-bukkit", "7.0.13") // WorldGuard

    implementation("net.kyori","adventure-platform-bukkit","4.3.4") // Adventure API

    implementation("de.tr7zw","item-nbt-api","2.14.1") // NBT-API
    implementation("com.zaxxer","HikariCP","6.2.1") // HikariCP

    implementation("com.mysql","mysql-connector-j", "9.2.0") // MySQL
    implementation("org.xerial","sqlite-jdbc", "3.49.1.0") // SQLite

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    archiveFileName.set("GrassLib-API.jar")
    archiveClassifier.set("all")
    mergeServiceFiles()

    relocate("de.tr7zw.changeme.nbtapi", "com.github.grassproject.grassLib.shadow")
    relocate("com.zaxxer.hikari", "com.github.grassproject.grassLib.shadow")
    relocate("org.jetbrains.exposed", "com.github.grassproject.grassLib.shadow")

    exclude("kotlin/**")
    exclude("org/**")
    minimize {
        exclude(dependency("com.github.grassproject.*:.*"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = groupId
            artifactId = "GrassLib"
            version = version

            pom {
                name.set("GrassLib")
                description.set("Library for Minecraft plugin")
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
                scm {
                    connection.set("scm:git:git://github.com/GrassProject/GrassLib.git")
                    developerConnection.set("scm:git:ssh://github.com/GrassProject/grasslib.git")
                    url.set("https://github.com/GrassProject/GrassLib")
                }
            }
        }
    }
    repositories {
        mavenLocal()
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = property("mavenCentralUserName").toString()
                password = property("mavenCentralPassword").toString()
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}