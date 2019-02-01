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
    val lombokDependency = "org.projectlombok:lombok:1.18.2"

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

    register<Jar>("sourcesJar") {
        from(sourceSets.main.get().allJava)
        archiveClassifier.set("sources")
    }

    register("delombok") {
        group = "delombok"

        doLast({
            ant.withGroovyBuilder {
                "taskdef"(
                        "name" to "delombok",
                        "classname" to "lombok.delombok.ant.Tasks\$Delombok",
                        "classpath" to project.configurations.compileOnly.asPath)
                "mkdir"("dir" to "build/src-delomboked")
                "delombok"(
                        "verbose" to "true",
                        "encoding" to "UTF-8",
                        "to" to "build/src-delomboked",
                        "from" to "src/main/java"
                ) {
                    "format"("value" to "generateDelombokComment:skip")
                    "format"("value" to "generated:generate")
                    "format"("value" to "javaLangAsFQN:skip")
                }
            }
        })
    }
}