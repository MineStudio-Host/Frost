plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
}

group = "host.minestudio"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    compileOnly(project(":api"))
    // Make sure you check the version on this from the docs.
    // This may be and probably is outdated. We just depend
    // on the project internally, and rarely update this value.
    // So please, do NOT leave this like this. Go find the latest
    // version. Otherwise, there's about a 9/10 chance this entire
    // shard breaks. kthx
    compileOnly("host.minestudio:frost-snapshots:39b860741c")
    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.2.0")

    compileOnly("dev.expx:minestom-invs:1.0.6")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
