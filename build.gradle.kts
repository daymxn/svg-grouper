// Copyright 2023 Daymon Littrell-Reyes
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.beryx.runtime.JPackageTask
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
    alias(libs.plugins.runtime)
    id("openrndr")
}

group = "com.daymxn"
version = "1.0.0"

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(kotlin("stdlib-jdk8"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
}

application {
    mainClass.set("MainKt")
}

tasks {
    withType<ShadowJar> {
        manifest{
            attributes["Main-Class"] = "MainKt"
            attributes["Implementation-Version"] = project.version
        }
        minimize {
            exclude(dependency("org.openrndr:openrndr-gl3:.*"))
            exclude(dependency("org.jetbrains.kotlin:kotlin-reflect:.*"))
        }
    }
    withType<JPackageTask> {
        doLast {
            val destPath = if(OperatingSystem.current().isMacOsX) "build/jpackage/svg-grouper.app/Contents/Resources/data" else "build/jpackage/svg-grouper/data"

            copy {
                from("data") {
                    include("**/*")
                }
                into(destPath)
            }
        }
    }
}

runtime {
    jpackage {
        imageName = "svg-grouper"
        imageOptions = listOf("--icon", "data/icon.ico")
        skipInstaller = true
        if (OperatingSystem.current().isMacOsX) {
            jvmArgs.add("-XstartOnFirstThread")
            jvmArgs.add("-Duser.dir=${"$"}APPDIR/../Resources")
        }
    }
    options.set(listOf("--strip-debug", "--compress", "1", "--no-header-files", "--no-man-pages"))
    modules.set(listOf("jdk.unsupported", "java.management", "java.desktop"))
}

task("createExecutable") {
    group = "build"
    dependsOn("jpackage")
}
