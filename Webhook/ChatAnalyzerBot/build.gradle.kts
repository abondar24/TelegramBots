import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import io.micronaut.gradle.docker.MicronautDockerfile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("org.jetbrains.kotlin.kapt") version "1.9.23"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.3.5"
    id("io.micronaut.aot") version "4.3.5"
    id("io.micronaut.docker") version "4.3.5"
    id("io.micronaut.graalvm") version "4.3.5"
    id("com.palantir.git-version") version "3.0.0"
}

version = "1.0"
group = "org.abondar.experimental.telegrambots"

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val commitId = versionDetails().gitHash



val kotlinVersion=project.properties.get("kotlinVersion")
val jedisVersion=project.properties.get("jedisVersion")
val mockitoKotlinVersion=project.properties.get("mockitoKotlinVersion")

repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.openapi:micronaut-openapi")
    kapt("io.micronaut.serde:micronaut-serde-processor")
    kapt("io.micronaut.validation:micronaut-validation-processor")

    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut.chatbots:micronaut-chatbots-telegram-http")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("redis.clients:jedis:${jedisVersion}")
    implementation("io.micronaut:micronaut-runtime")

    compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("io.micronaut.test:micronaut-test-rest-assured")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${mockitoKotlinVersion}")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:testcontainers")
}


application {
    mainClass.set("org.abondar.experimental.telegrambots.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    args("-Dmicronaut.environments=cloud")
    instruction ("ARG WEBHOOK_TOKEN")
    instruction ("ARG REDIS_HOST")
    instruction ("ARG REDIS_PORT")
    instruction ("ARG REDIS_USER")
    instruction ("ARG REDIS_PASSWORD")
    instruction ("ARG PORT")
}

tasks.named<DockerBuildImage>("dockerBuildNative") {
    images.add("registry.heroku.com/chatanalyzerbot/web:$version-$commitId")
}

tasks.named<MicronautDockerfile>("dockerfile") {
    args("-Dmicronaut.environments=cloud")
    instruction ("ARG WEBHOOK_TOKEN")
    instruction ("ARG REDIS_HOST")
    instruction ("ARG REDIS_PORT")
    instruction ("ARG REDIS_USER")
    instruction ("ARG REDIS_PASSWORD")
    instruction ("ARG PORT")

}

tasks.named<DockerBuildImage>("dockerBuild") {
    images.add("registry.heroku.com/chatanalyzerbot/web:$version-$commitId")
}


graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("org.abondar.experimental.telegrambots.*")
    }
    aot {
    // Please review carefully the optimizations enabled below
    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
    }
}



