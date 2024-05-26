plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.example.MainKt"
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}