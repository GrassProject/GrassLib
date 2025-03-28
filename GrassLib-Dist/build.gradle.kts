plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.0.0-beta10"
}

group = rootProject.group
version = rootProject.version

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.21.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(project(":GrassLib-API"))
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    archiveFileName.set("GrassLib-${rootProject.version}.jar")
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

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
