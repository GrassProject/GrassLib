plugins {
    kotlin("jvm") version "2.1.0"
    id("com.gradleup.shadow") version "9.0.0-beta10"
    id("maven-publish")
}

group = "com.github.grassproject"
version = "1.0"

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
}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.21.1-R0.1-SNAPSHOT") // Paper
    compileOnly("io.lumine","Mythic-Dist", "5.6.1") // MythicMob
    compileOnly("dev.lone","api-itemsadder", "4.0.2-beta-release-11") // ItemsAdder
    compileOnly("io.th0rgal","oraxen","1.189.0") // Oraxen
    compileOnly("com.nexomc", "nexo","1.0.0") // Nexo
    compileOnly("io.lumine", "MythicLib-dist","1.6.2-SNAPSHOT") // MythicLib
    compileOnly("net.Indyuce", "MMOItems-API","6.9.5-SNAPSHOT") // MMOItems
    compileOnly("com.arcaniax","HeadDatabase-API", "1.3.2") // HeadDatabase
    compileOnly("com.comphenix.protocol:ProtocolLib:5.3.0") // ProtocolLib

    implementation("de.tr7zw","item-nbt-api","2.14.1") // NBT-API

}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    archiveFileName.set("GrassLib.jar")
    archiveClassifier.set("all")
    mergeServiceFiles()

    relocate("de.tr7zw.changeme.nbtapi", "com.github.teamgrass25.lib.shadow")

    exclude("kotlin/**")
//    dependencies {
//        include("")
//    }
    // from(rootProject.file("LICENSE"))

//    destinationDirectory=file("C:\\Users\\aa010\\Desktop\\Grass\\plugins")
//    destinationDirectory=file("C:\\Users\\PC\\Desktop\\Test_Server\\21.1\\plugins")
    minimize()
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
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