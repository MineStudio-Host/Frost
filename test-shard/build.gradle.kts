plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "2.1.20-1.0.32"
}

group = "host.minestudio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    compileOnly(project(":api"))
    compileOnly("net.minestom:minestom-snapshots:7320437640")
    compileOnly("com.google.auto.service:auto-service:1.1.1")
    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.2.0")

    compileOnly("org.apache.maven.resolver:maven-resolver-impl:1.9.7")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}