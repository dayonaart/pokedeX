/*
 * Copyright (c) 2023 (  Dayona )
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

val baseUrl: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
    rootDir
).getProperty("BASE_URL")
android {
    namespace = "id.dayona.pokedex"
    compileSdk = 34

    defaultConfig {
        applicationId = "id.dayona.pokedex"
        minSdk = 24
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", baseUrl)
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", baseUrl)

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}
val roomVersion = "2.5.2" /* Room Database */
val navVersion = "2.7.2" /* Nav Compose */
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.activity:activity-compose:1.7.2") /* compose and material */
    implementation(platform("androidx.compose:compose-bom:2023.03.00")) /* compose and material */
    implementation("androidx.compose.ui:ui") /* compose and material */
    implementation("androidx.compose.ui:ui-graphics") /* compose and material */
    implementation("androidx.compose.ui:ui-tooling-preview") /* compose and material */
    implementation("androidx.compose.material3:material3") /* compose and material */
    implementation("androidx.compose.material:material") /* compose and material */
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00")) /* compose and material */
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") /* compose and material */
    debugImplementation("androidx.compose.ui:ui-tooling") /* compose and material */
    debugImplementation("androidx.compose.ui:ui-test-manifest") /* compose and material */
    implementation("com.google.dagger:hilt-android:2.48")    /* dagger hilt */
    kapt("com.google.dagger:hilt-android-compiler:2.48") /* dagger hilt */
    implementation("androidx.room:room-runtime:$roomVersion") /* Room Database */
    annotationProcessor("androidx.room:room-compiler:$roomVersion") /* Room Database */
    ksp("androidx.room:room-compiler:$roomVersion") /* Room Database */
    implementation("androidx.navigation:navigation-compose:$navVersion") /* Nav Compose */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")  /* Network */
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")  /* Network */
    implementation("com.google.code.gson:gson:2.10.1")  /* GSON */
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))  /* Network */
    implementation("com.squareup.okhttp3:okhttp")  /* Network */
    implementation("com.squareup.okhttp3:logging-interceptor")  /* Network */
    implementation("io.coil-kt:coil-compose:2.4.0") /* COIL COMPOSE*/
    implementation("io.coil-kt:coil-gif:2.4.0") /* COIL GIF*/

}
kapt {
    correctErrorTypes = true
}