import org.apache.commons.lang3.SystemUtils
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

plugins {
    idea
    java
    id("gg.essential.loom") version "0.10.0.+"
    id("dev.architectury.architectury-pack200") version "0.1.3"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "1.8.0"
    id("com.bnorm.power.kotlin-power-assert") version "0.13.0"
    id("net.kyori.blossom") version "1.3.2"
}

//Constants:
val mod_name: String by project
val mod_version: String by project
val mod_id: String by project
val skyhanni_version: String by project

blossom {
    replaceToken("@MOD_VER@", mod_version)
    replaceToken("@MOD_NAME@", mod_name)
    replaceToken("@MOD_ID@", mod_id)
}

group = "com.$mod_id"
version = mod_version
val modid = mod_id
val skyHanniVersion = skyhanni_version

val gitHash by lazy {
    val baos = ByteArrayOutputStream()
    exec {
        standardOutput = baos
        commandLine("git", "rev-parse", "--short", "HEAD")
        isIgnoreExitValue = true
    }
    baos.toByteArray().decodeToString().trim()
}

// Toolchains:
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

sourceSets.main {
    output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
    java.srcDir(layout.projectDirectory.dir("src/main/kotlin"))
    kotlin.destinationDirectory.set(java.destinationDirectory)
}

// Dependencies:

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.spongepowered.org/maven/")
    // If you don't want to log in with your real minecraft account, remove this line
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
    maven("https://jitpack.io") {
        content {
            includeGroupByRegex("com\\.github\\..*")
        }
    }
    maven("https://repo.nea.moe/releases")
    maven("https://maven.notenoughupdates.org/releases") // NotEnoughUpdates (dev env)
    maven("https://maven.teamresourceful.com/repository/thatgravyboat/") // DiscordIPC
}

val shadowImpl: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

val shadowModImpl: Configuration by configurations.creating {
    configurations.modImplementation.get().extendsFrom(this)
}

val devenvMod: Configuration by configurations.creating {
    isTransitive = false
    isVisible = false
}

val headlessLwjgl by configurations.creating {
    isTransitive = false
    isVisible = false
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

    headlessLwjgl(libs.headlessLwjgl)

    shadowImpl("org.spongepowered:mixin:0.7.11-SNAPSHOT") {
        isTransitive = false
    }
    annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT")

    // If you don't want to log in with your real minecraft account, remove this line
    runtimeOnly("me.djtheredstoner:DevAuth-forge-legacy:1.1.2")

    modCompileOnly("com.github.hannibal002:notenoughupdates:4957f0b:all") {
        exclude(module = "unspecified")
        isTransitive = false
    }
    devenvMod("com.github.NotEnoughUpdates:NotEnoughUpdates:2.4.0:all") {
        exclude(module = "unspecified")
        isTransitive = false
    }

    modCompileOnly("com.github.hannibal002:SkyHanni:$skyHanniVersion:") {
        exclude(group = "null", module = "unspecified")
        isTransitive = false
    }
    devenvMod("com.github.hannibal002:SkyHanni:$skyHanniVersion:") {
        exclude(group = "null", module = "unspecified")
        isTransitive = false
    }
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
            enableLanguageFeature("BreakContinueInInlineLambdas")
        }
    }
}

// Minecraft configuration:
loom {
    launchConfigs {
        "client" {
            property("mixin.debug", "true")
            property("devauth.configDir", rootProject.file(".devauth").absolutePath)
            arg("--tweakClass", "org.spongepowered.asm.launch.MixinTweaker")
            arg("--mods", devenvMod.resolve().joinToString(",") { it.relativeTo(file("run")).path })
        }
    }
    forge {
        pack200Provider.set(dev.architectury.pack200.java.Pack200Adapter())
        mixinConfig("mixins.$modid.json")
    }
    @Suppress("UnstableApiUsage")
    mixin {
        defaultRefmapName.set("mixins.$modid.refmap.json")
    }
    runConfigs {
        "client" {
            if (SystemUtils.IS_OS_MAC_OSX) {
                vmArgs.remove("-XstartOnFirstThread")
            }
            vmArgs.add("-Xmx4G")
        }
        "server" {
            isIdeConfigGenerated = false
        }
    }
}

tasks.processResources {
    inputs.property("mod_id", mod_id)
    inputs.property("mod_name", mod_name)
    inputs.property("version", version)
    filesMatching("mcmod.info") {
        expand(mapOf(
            "mod_id" to mod_id,
            "mod_name" to mod_name,
            "version" to version
        ))
    }
}

tasks.compileJava {
    dependsOn(tasks.processResources)
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}


tasks.withType(Jar::class) {
    destinationDirectory.set(project.layout.buildDirectory.dir("badjars"))
    archiveBaseName.set(mod_id)
    manifest.attributes.run {
        this["FMLCorePluginContainsFMLMod"] = "true"
        this["ForceLoadAsMod"] = "true"

        this["TweakClass"] = "org.spongepowered.asm.launch.MixinTweaker"
        this["MixinConfigs"] = "mixins.$modid.json"
    }
}

val remapJar by tasks.named<net.fabricmc.loom.task.RemapJarTask>("remapJar") {
    archiveClassifier.set("")
    from(tasks.shadowJar)
    input.set(tasks.shadowJar.get().archiveFile)
}

tasks.shadowJar {
    destinationDirectory.set(layout.buildDirectory.dir("badjars"))
    archiveClassifier.set("all-dev")
    configurations = listOf(shadowImpl, shadowModImpl)
    doLast {
        configurations.forEach {
            println("Config: ${it.files}")
        }
    }
    exclude("META-INF/versions/**")
    mergeServiceFiles()
}

tasks.jar {
    archiveClassifier.set("nodeps")
    destinationDirectory.set(layout.buildDirectory.dir("badjars"))
}
tasks.assemble.get().dependsOn(tasks.remapJar)

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val sourcesJar by tasks.creating(Jar::class) {
    destinationDirectory.set(layout.buildDirectory.dir("badjars"))
    archiveClassifier.set("src")
    from(sourceSets.main.get().allSource)
}
