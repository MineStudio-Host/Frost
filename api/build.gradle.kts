plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka") version "2.0.0"
}

group = "host.minestudio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    compileOnly("net.minestom:minestom-snapshots:87f6524aeb") // MINESTOM

    compileOnly("org.apache.maven:maven-resolver-provider:3.9.9")
    compileOnly("org.apache.maven.resolver:maven-resolver-impl:2.0.8")
    compileOnly("org.apache.maven.resolver:maven-resolver-connector-basic:1.9.7")
    compileOnly("org.apache.maven.resolver:maven-resolver-transport-file:1.9.22")
    compileOnly("org.apache.maven.resolver:maven-resolver-transport-http:1.9.22")
    compileOnly("org.apache.maven.resolver:maven-resolver-transport-classpath:1.9.22")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn("dokkaHtml")
}