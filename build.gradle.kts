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

    register("delombok") {
        group = "delombok"

        doLast({
            ant.withGroovyBuilder {
                val target = File(buildDir, "delomboked")

                "taskdef"(
                        "name" to "delombok",
                        "classname" to "lombok.delombok.ant.Tasks\$Delombok",
                        "classpath" to sourceSets.main.get().compileClasspath.asPath)
                "mkdir"("dir" to target)
                "delombok"(
                        "verbose" to false,
                        "encoding" to "UTF-8",
                        "to" to target,
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
        from(sourceSets.main.get().allJava)
        archiveClassifier.set("sources")
    }
}