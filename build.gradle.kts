import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    kotlin("jvm") version "2.0.0"
    id("maven-publish")
}

group = "dev.pandoras.lib"
version = "0.0.1"

//####### Load properties from gradle-local.properties
// Function to load a properties file
fun loadProperties(file: File): Properties {
    val properties = Properties()
    if (file.exists()) {
        FileInputStream(file).use { properties.load(it) }
    }
    return properties
}

// Load the standard gradle.properties
val properties = loadProperties(file("gradle.properties"))

// Load the local gradle-local.properties if present
val localProperties = loadProperties(file("gradle-local.properties"))
properties.putAll(localProperties)

// Make the properties available as project properties
properties.forEach { key, value ->
    project.extensions.extraProperties.set(key.toString(), value)
}
//####### Load properties from gradle-local.properties

val pandoraRepoUrl = "https://repo.pandoras.dev/repository/maven2"
val publishRepoName = "PandorasMinecraftLib"

val nexusUsername = System.getenv("NEXUS_USERNAME") ?: project.findProperty("nexusUsername") as String
val nexusPassword = System.getenv("NEXUS_PASSWORD") ?: project.findProperty("nexusPassword") as String


repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }
    maven {
        url = uri(pandoraRepoUrl)
        credentials {
            username = nexusUsername
            password = nexusPassword
        }
    }
}

val pandorasLibVersion = "0.0.18"
val velocityVersion = "3.3.0-SNAPSHOT"
val paperVersion = "1.21-R0.1-SNAPSHOT"
val bukkitVersion = "1.21-R0.1-SNAPSHOT"

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    //pandoras Lib
    implementation("dev.pandoras.lib:PandorasLib:$pandorasLibVersion")

    //velocity
    implementation("com.velocitypowered:velocity-api:$velocityVersion")

    //paper
    compileOnly("io.papermc.paper:paper-api:$paperVersion")

    //bukkit
    //implementation("org.bukkit:bukkit:$bukkitVersion")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = publishRepoName
            url = uri(pandoraRepoUrl.replace("maven2", publishRepoName))
            credentials {
                username = nexusUsername
                password = nexusPassword
            }
        }
    }
}
