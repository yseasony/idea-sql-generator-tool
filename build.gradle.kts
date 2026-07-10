import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType

plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.17.0"
}

group = "org.yseasony"
version = "2.3.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaUltimate("2024.2.4")
        bundledPlugin("com.intellij.database")
        pluginVerifier()
    }
    testImplementation("junit:junit:4.13.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:deprecation")
}

intellijPlatform {
    pluginConfiguration {
        name = "Sql Generator"
        ideaVersion {
            sinceBuild = "242"
            untilBuild = provider { null }
        }
    }
    pluginVerification {
        ides {
            create(IntelliJPlatformType.IntellijIdeaUltimate, "2026.1.4")
        }
    }
    publishing {
        token = providers.gradleProperty("intellijPlatformPublishingToken")
            .orElse(providers.environmentVariable("PUBLISH_TOKEN"))
    }
}
