plugins {
    java
    kotlin("jvm") version "1.3.61"
}

group = "exercise"
java.sourceCompatibility = JavaVersion.VERSION_1_8
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    // maven { url "https://dl.bintray.com/kotlin/ktor" }
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-netty:1.3.2")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("commons-io:commons-io:2.5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("io.ktor:ktor-server-test-host:1.3.2")
    testImplementation("org.mockito:mockito-core:2.23.4")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}