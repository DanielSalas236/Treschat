import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
	id("org.sonarqube") version "3.1.1"
	id("jacoco")
}

group = "com.uniajc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("org.flywaydb:flyway-core")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(group = "org.mockito", name = "mockito-all", version = "2.0.2-beta")
	implementation("org.mockito:mockito-core:3.8.0")
	testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.7.1")
	testImplementation(group = "org.springframework.security", name = "spring-security-test", version = "5.3.8.RELEASE")
	implementation(group = "io.springfox", name = "springfox-swagger2", version = "2.9.2")
	implementation(group = "io.springfox", name = "springfox-swagger-ui", version = "2.10.5")
	implementation(group = "javax.validation", name = "validation-api", version = "2.0.0.Final")
	implementation(group = "org.springframework.boot", name = "spring-boot-starter-validation", version = "2.4.3")
	implementation(group = "org.modelmapper", name = "modelmapper", version = "2.3.9")
	implementation(group = "io.jsonwebtoken", name = "jjwt", version = "0.9.1")
	implementation(group = "org.springframework.boot", name = "spring-boot-starter-security", version = "2.4.4")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport{
	reports{
		xml.isEnabled = true
	}
	dependsOn(tasks.test)
}
