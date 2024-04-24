import org.jooq.meta.jaxb.Logging

plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.openapi.generator") version "7.1.0"
    id("nu.studer.jooq") version "8.2.1"
}

group = "com.lingo.craft.main"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

val springCloudVersion by extra("2023.0.0")

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.postgresql:postgresql")
    implementation("org.jooq:jooq")
    implementation("org.jooq:jooq-codegen:3.18.7")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0-M1")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.apache.tika:tika-core:2.9.2")
    implementation("org.apache.tika:tika-parsers:2.9.2")
    implementation("org.apache.tika:tika-parsers-standard-package:2.9.2")
    implementation("org.apache.tika:tika-langdetect:2.9.2")
    implementation("org.apache.tika:tika-serialization:2.9.2")
    implementation("org.apache.tika:tika-translate:2.9.2")
    implementation("org.apache.tika:tika-langdetect-optimaize:2.9.2")
    implementation("org.apache.tika:tika-eval-core:2.9.2")
    implementation("org.apache.tika:tika-transcribe-aws:2.9.2")

    jooqGenerator("org.postgresql:postgresql")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("org.testcontainers:junit-jupiter:1.19.3")
    testImplementation("org.testcontainers:postgresql:1.19.3")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val spec: String = project.file("src/main/resources/api/api-spec.yaml").absolutePath
val generatedSourcesDir: String = project.file(
        layout.buildDirectory.dir("generated")
).absolutePath
val jooqSourceDir: String = "src/main/jooq"

openApiGenerate {
    generatorName = "spring"
    inputSpec = spec
    outputDir = generatedSourcesDir
    modelPackage = "com.diabolocom.release.openapi.model"
    configOptions.set(
            mapOf(
                    "dateLibrary" to "java8",
                    "delegatePattern" to "true",
                    "library" to "spring-boot",
                    "useSpringBoot3" to "true",
                    "useSpringfox" to "false",
                    "interfaceOnly" to "true",
                    "modelDocs" to "false",
                    "apiDocs" to "false",
                    "modelTests" to "false",
                    "apiTests" to "false"
            )
    )
}



sourceSets {
    getByName("main") {
        java {
            srcDir("$generatedSourcesDir/src")
            srcDir(jooqSourceDir)
        }
    }
}

tasks {
    val openApiGenerate by getting

    val compileJava by getting {
        dependsOn(openApiGenerate)
    }
}

tasks.register("cleanGeneratedFiles") {
    doLast {
        fileTree(generatedSourcesDir).matching {
            include(
                    "**/org/openapitools/api/**",
                    "**/.openapi-generator-ignore",
                    "**/README.md",
                    "**/pom.xml"
            )
        }.forEach { it.delete() }
        // Delete the empty package directory
        file("$generatedSourcesDir/src/main/java/org").deleteRecursively()
    }
}

tasks.named("openApiGenerate") {
    dependsOn("clean")
}

tasks.named("build") {
    dependsOn("openApiGenerate")
    dependsOn("cleanGeneratedFiles")
}

jooq {
    version.set("3.18.7")
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation = false
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/lingo_craft"
                    user = "postgres"
                    password = "Admin123"
                }
//                jdbc.apply {
//                    driver = "org.postgresql.Driver"
//                    url = "jdbc:postgresql://localhost:6432/lingo_craft"
//                    user = "lingo_read"
//                    password = "lingo_read"
//                }
                generator.apply {
                    database.apply {
                        inputSchema = "public"
                        // Add additional configurations specific to PostgreSQL if needed
                    }
                    target.apply {
                        packageName = "com.lingo.craft"
                        directory = "src/main/jooq"
                    }

                }
            }
        }
    }
}

tasks.bootJar {
    destinationDirectory.set(file("docker-dir"))
    archiveFileName.set("lingo_craft.jar")
    enabled = true
}
