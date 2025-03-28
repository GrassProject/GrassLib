plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.0.0-beta10"
}

group = rootProject.group
version = rootProject.version

val exposedVersion: String by project

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation(project(":GrassLib-API"))
//    implementation("com.github.grassproject:GrassLib:1.0")\

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

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.shadowJar {
    archiveFileName.set("GrassLib-test.jar")
    archiveClassifier.set("all")
    mergeServiceFiles()

    exclude("kotlin/**")
    dependencies {
        include(project(":GrassLib-API"))
    }
    // from(rootProject.file("LICENSE"))

    // destinationDirectory=file("C:\\Users\\aa010\\Desktop\\Grass\\plugins")
//    destinationDirectory=file("C:\\Users\\PC\\Desktop\\Test_Server\\21.1\\plugins")
    minimize {
        exclude(dependency("com.github.grassproject.*:.*"))
    }
}