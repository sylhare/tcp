import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    `maven-publish`
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

group = "tcp"
java.sourceCompatibility = JavaVersion.VERSION_11
version = "1.0-SNAPSHOT"

application {
    mainClass.set("spring.ApplicationKt")
}

repositories {
    mavenCentral()
    // maven { url "https://dl.bintray.com/kotlin/ktor" }
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-netty:1.6.0")
    implementation("io.ktor:ktor-network:1.6.0")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("commons-io:commons-io:2.5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("io.ktor:ktor-server-test-host:1.6.0")
    testImplementation("org.mockito:mockito-core:2.23.4")

    implementation("org.springframework.integration:spring-integration-core")
    implementation("org.springframework.integration:spring-integration-ip")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
