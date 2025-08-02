import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost


plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.vanniktech.maven.publish") version "0.34.0"
    signing
    kotlin("plugin.serialization") version "1.9.25"
}

group = "host.minestudio"
version = System.getenv("VERSION") ?: "dev"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Internal dependencies
    compileOnly(libs.maven.resolver.provider)
    compileOnly(libs.maven.resolver.connector)
    compileOnly(libs.maven.resolver.file)
    compileOnly(libs.maven.resolver.http)
    compileOnly(libs.maven.resolver.classpath)

    // Exposed APIs
    api(libs.maven.resolver.impl)
    api(libs.minestom) // MINESTOM SNAPSHOTS
    api(libs.kotlinx.serialization) // JSON SERIALIZER
    api(libs.auto.service) // AUTO SERVICE
}

mavenPublishing {
    configure(KotlinJvm(
        javadocJar = JavadocJar.Dokka("dokkaHtml"),
        sourcesJar = true
    ))
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    coordinates("host.minestudio.ghosts", "frost-snapshots", project.version.toString())

    signAllPublications()

    pom {
        name.set("Frost API")
        description.set("A library for creating and managing Minestom servers.")
        inceptionYear.set("2025")
        url.set("https://www.minestudio.host")

        developers {
            developer {
                id.set("cammyzed")
                name.set("Cam M")
                url.set("https://expx.dev")
                email.set("cam@expx.dev")
            }
            developer {
                id.set("thecodingduck")
                name.set("Colton H")
                email.set("zcoltonhirsch@gmail.com")
            }
        }
        licenses {
            license {
                name.set("No License")
                url.set("https://www.minestudio.host/")
            }
        }
        scm {
            url.set("scm:git:github.com/MineStudio-Host/Frost.git")
            developerConnection.set("scm:git:ssh://github.com/MineStudio-Host/Frost.git")
            connection.set("scm:git:github.com/MineStudio-Host/Frost.git")
        }
    }
}

signing {
    isRequired = System.getenv("CI") != null
    val privateKey = System.getenv("GPG_PRIVATE_KEY")
    val keyPassphrase = System.getenv("GPG_PRIVATE_KEY_PASSPHRASE")
    if (privateKey != null && keyPassphrase != null) {
        useInMemoryPgpKeys(privateKey, keyPassphrase)
    } else {
        logger.warn("GPG keys not found in environment variables. Signing will be skipped.")
    }
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
