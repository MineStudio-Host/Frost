import java.time.Duration

plugins {
    kotlin("jvm") version "2.1.21"
    id("com.gradleup.shadow") version "8.3.6"
    id("io.sentry.jvm.gradle") version "3.14.0"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = "host.minestudio"
version = System.getenv("VERSION") ?: "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.slf4j:slf4j-api:2.0.17")                             // SLF4J API (Logging)
    implementation("ch.qos.logback:logback-classic:1.5.18")                  // Logback implementation
    implementation("ch.qos.logback:logback-core:1.5.18")                     // Logback core

    implementation("net.minestom:minestom-snapshots:42e0d21266")             // MINESTOM

    implementation("org.eclipse.collections:eclipse-collections:12.0.0.M3")  // ECLIPSE COLLECTIONS
    implementation("io.socket:socket.io-client:2.1.2")                       // SOCKET.IO

    implementation("com.google.auto.service:auto-service:1.1.1")             // AUTO SERVICE

    implementation("org.apache.maven:maven-resolver-provider:3.9.9")
    implementation("org.apache.maven.resolver:maven-resolver-impl:1.9.23")
    implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.9.22")
    implementation("org.apache.maven.resolver:maven-resolver-transport-file:1.9.22")
    implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.9.23")
    implementation("org.apache.maven.resolver:maven-resolver-transport-classpath:2.0.9")

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