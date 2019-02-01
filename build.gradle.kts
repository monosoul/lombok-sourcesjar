group = "com.github.monosoul"
version = "1.0.0"

plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    val lombokDependency = "org.projectlombok:lombok:1.18.4"

    annotationProcessor(lombokDependency)
    compileOnly(lombokDependency)
}

repositories {
    mavenCentral()
}

tasks {
    "jar"(Jar::class) {
        manifest {
            attributes(
                    Pair("Main-Class", "com.github.monosoul.lombok.sourcesjar.Main")
            )
        }
    }
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allJava)
    archiveClassifier.set("sources")
}