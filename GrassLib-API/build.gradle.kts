plugins {
    kotlin("jvm") version "2.1.10"
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

    implementation("de.tr7zw","item-nbt-api","2.14.1") // NBT-API
    implementation("com.zaxxer","HikariCP","6.2.1") // HikariCP

    implementation("com.mysql","mysql-connector-j", "9.2.0") // MySQL
    implementation("org.xerial","sqlite-jdbc", "3.49.1.0") // SQLite

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

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

    exclude("kotlin/**")
    exclude("org/**")
    minimize {
        exclude(dependency("com.github.grassproject.*:.*"))
    }
}