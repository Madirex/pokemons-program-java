plugins {
    id("java")
}

group = "com.madirex"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.28")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.h2database:h2:2.2.224")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
}

tasks.test {
    useJUnitPlatform()
}