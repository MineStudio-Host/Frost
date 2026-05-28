import java.time.Duration

plugins {
    kotlin("jvm") version "2.2.0"
    id("com.gradleup.shadow") version "8.3.11"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = "host.minestudio"
version = System.getenv("VERSION") ?: "dev"


repositories {
    maven("https://jitpack.io") // for minestom
    maven("https://repo.hypera.dev/snapshots/") // spark-minestom
    maven("https://repo.lucko.me/") // spark-common
    maven("https://oss.sonatype.org/content/repositories/snapshots/") // spark-common's dependencies

    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("dev.lu15:spark-minestom:1.10-SNAPSHOT")
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
    implementation(libs.logback.core)
    implementation(libs.minestom)
    implementation(libs.eclipse.collections)
    implementation(libs.socket.io)
    implementation(libs.auto.service)
    implementation(libs.snakeyaml)

    implementation(libs.maven.resolver.provider)
    implementation(libs.maven.resolver.impl)
    implementation(libs.maven.resolver.connector)
    implementation(libs.maven.resolver.file)
    implementation(libs.maven.resolver.http)
    implementation(libs.maven.resolver.classpath)

    implementation(project(":api"))
}

kotlin {
    jvmToolchain(21)
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "host.minestudio.frost.ServerKt"
        }
    }
    build {
        dependsOn("shadowJar")
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
        archiveFileName.set("${project.name}-${project.version}.jar")
        doLast {
            val targetDir = file("$rootDir/.run/").apply { mkdirs() }

            archiveFile.get().asFile.apply {
                copyTo(
                    target = file("$targetDir/$name"),
                    overwrite = true
                )
            }
        }
    }
}