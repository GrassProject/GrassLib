plugins {
    `maven-publish`
    signing
}

tasks.shadowJar {
    dependsOn(tasks.named("publishApiPublicationToServerRepository"))
    dependsOn(tasks.named("publishCorePublicationToServerRepository"))
}

publishing {
    publications {
//        return@publications
        fun MavenPublication.setup(target: Project) {
            from(target.components["java"])
            groupId = rootProject.group.toString()
            artifactId = "GrassLib"
            version = rootProject.version.toString()

            pom {
                name.set(target.name)
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
                    developerConnection.set("scm:git:ssh://github.com:GrassProject/grasslib.git")
                    url.set("https://github.com/GrassProject/GrassLib")
                }
            }
        }

        create<MavenPublication>("maven") {

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
    isRequired=true
//    sign(publishing.publications["GrassLib-API"], publishing.publications["GrassLib-Dist"])
}