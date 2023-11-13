plugins {
    kotlin("jvm") version "1.9.20-Beta"
    id("java-library")
    id("maven-publish")
    id("io.papermc.paperweight.userdev") version "1.5.6"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "net.bloxsims"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib"))
}

tasks {
    named("assemble") {
        dependsOn(named("reobfJar"))
    }

    build {
        dependsOn("shadowJar")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}


publishing {
    repositories {
        maven {
            url = uri("../../Repo") // Path to your local repository directory
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}