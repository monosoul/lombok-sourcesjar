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

    val delombok by creating {
        group = "lombok"

        val delombokTarget by extra { File(buildDir, "delomboked") }
        
        doLast({
            ant.withGroovyBuilder {
                "taskdef"(
                        "name" to "delombok",
                        "classname" to "lombok.delombok.ant.Tasks\$Delombok",
                        "classpath" to sourceSets.main.get().compileClasspath.asPath)
                "mkdir"("dir" to delombokTarget)
                "delombok"(
                        "verbose" to false,
                        "encoding" to "UTF-8",
                        "to" to delombokTarget,
                        "from" to sourceSets.main.get().java.srcDirs.first().absolutePath
                ) {
                    "format"("value" to "generateDelombokComment:skip")
                    "format"("value" to "generated:generate")
                    "format"("value" to "javaLangAsFQN:skip")
                }
            }
        })
    }

    register<Jar>("sourcesJar") {
        dependsOn(delombok)

        val delombokTarget: File by delombok.extra
        from(delombokTarget)
        archiveClassifier.set("sources")
    }
}