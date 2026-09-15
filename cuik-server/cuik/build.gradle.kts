plugins {
    java
    id("com.gradleup.shadow") version "9.6.1"
    application
    eclipse
}

repositories {
    mavenCentral()
    // maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation ("com.auth0:java-jwt:4.6.1")
    implementation("com.mysql:mysql-connector-j:26.7.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("org.eclipse.jetty:jetty-server:12.0.12")
    // implementation("org.eclipse.jetty.websocket:jetty-websocket-jetty-server:12.0.12")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "cuik.App"
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    minimize {
        exclude(dependency("org.slf4j:.*:.*"))
    }
    mergeServiceFiles()
    archiveFileName = "cuik.jar"
    destinationDirectory = File("$rootDir/")
}

application {
    mainClass = "cuik.App"
}
