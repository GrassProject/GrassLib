plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.0.0-beta10"
    id("maven-publish")
    id("signing")
}

group = "com.github.grassproject"
version = "1.0"

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
    }

//    signing {
//        useInMemoryPgpKeys(
//            property("signing.keyId") as String?,
//            property("signing.secretKeyRingFile") as String?,
////            property("signing.inMemoryKey") as String?,
//            property("signing.password") as String?
//        )
//        sign(publishing.publications["maven"])
//    }
//    signing {
//        sign(publishing.publications["maven"])
//    }
}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn("shadowJar")
}

//mavenPublishing {
//    coordinates(
//        groupId = project.group as String,
//        artifactId = project.name,
//        version = project.version as String
//    )
//
//    pom {
//        name.set("GrassLib")
//        description.set("Library for Minecraft plugin")
//        url.set("https://github.com/GrassProject/GrassLib")
//
//        licenses {
//            license {
//                name.set("MIT License")
//                url.set("https://opensource.org/licenses/MIT")
//            }
//        }
//
//        developers {
//            developer {
//                id.set("apo2073")
//                name.set("APO2073")
//                email.set("apo2073@outlook.com")
//            }
//            developer {
//                id.set("mrjimin")
//                name.set("Jimin")
//                email.set("aa090402@naver.com")
//            }
//            developer {
//                id.set("wayggstar")
//                name.set("Wayggstar")
//                email.set("wayggstar@gmail.com")
//            }
//        }
//
//        scm {
//            connection.set("scm:git:git://github.com/GrassProject/GrassLib.git")
//            developerConnection.set("scm:git:ssh://github.com/GrassProject/grasslib.git")
//            url.set("https://github.com/GrassProject/GrassLib")
//        }
//    }
//
//    signAllPublications()
//}
//
//signing {
//    sign(publishing.publications)
//}