plugins {
    idea
    kotlin("jvm") version "2.1.21"
    id("com.gradleup.shadow") version "9.0.0-beta10"
    id("maven-publish")
    id("signing")
}

group = "com.github.grassproject"
version = "1.2.1"

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.gradleup.shadow")
    apply(plugin = "signing")
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://mvn.lumine.io/repository/maven-public/")
        maven("https://maven.devs.beer/")
        maven("https://repo.nexomc.com/releases")
        maven("https://repo.oraxen.com/releases")
        maven("https://nexus.phoenixdevt.fr/repository/maven-public/")
        maven("https://repo.codemc.io/repository/maven-public/")
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://jitpack.io")
        maven("https://repo.nightexpressdev.com/releases")
        maven("https://maven.enginehub.org/repo/")
        maven("https://repo.skriptlang.org/releases")

        maven("https://repo.codemc.org/repository/maven-public/")
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn("shadowJar")
}

idea {
    module {
        excludeDirs.addAll(allprojects.map { it.file("run") })
        excludeDirs.addAll(allprojects.map { it.buildDir })
        excludeDirs.addAll(allprojects.map { it.file(".gradle") })
    }
}