plugins {
    kotlin("jvm") version "2.1.10"
    id("com.gradleup.shadow") version "9.0.0-beta10"
}

group = rootProject.group
version = rootProject.version

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
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1") // Vault

    implementation("de.tr7zw","item-nbt-api","2.14.1") // NBT-API
    implementation("com.zaxxer","HikariCP","6.2.1") // HikariCP
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

    relocate("de.tr7zw.changeme.nbtapi", "com.github.teamgrass25.lib.shadow")
    exclude("kotlin/**")
    exclude("org/**")
    minimize()
}