plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "2.1.20-2.0.1"
}

group = "host.minestudio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    compileOnly(project(":api"))
    compileOnly("net.minestom:minestom-snapshots:87f6524aeb")
    compileOnly("com.google.auto.service:auto-service:1.1.1")
    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.2.0")

    compileOnly("org.apache.maven.resolver:maven-resolver-impl:2.0.8")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}