plugins {
    java
    id("com.gradleup.shadow") version "9.6.1"
    application
    eclipse
}

repositories {
    mavenCentral()
}

dependencies {
    //libs
    implementation ("com.auth0:java-jwt:4.6.1")
    implementation("com.mysql:mysql-connector-j:26.7.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("org.eclipse.jetty:jetty-server:12.0.12")
    implementation("de.mkammerer:argon2-jvm:2.12")
    implementation("org.freemarker:freemarker:2.3.35")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "cuik.App"
    }
}

eclipse {
    jdt {
        file {
            withProperties {
                setProperty("org.eclipse.jdt.core.compiler.codegen.methodParameters", "generate")
            }
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    // minimize {
    //     exclude(dependency("org.slf4j:.*:.*"))
    //     exclude(dependency("com.mysql:mysql-connector-j:.*"))
    // }
    mergeServiceFiles()
    archiveFileName = "cuik.jar"
    destinationDirectory = File("$rootDir/")
}

application {
    mainClass = "cuik.App"
}
