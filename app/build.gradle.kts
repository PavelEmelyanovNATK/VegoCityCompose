

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.emelyanov.vegocity"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        val compose_version: String by rootProject.extra
        kotlinCompilerExtensionVersion = compose_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")

    //Compose
    val compose_version: String by rootProject.extra
    val nav_version: String by rootProject.extra
    implementation ("androidx.compose.ui:ui:$compose_version")
    implementation ("androidx.compose.material3:material3:1.0.0-alpha13")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation ("androidx.compose.material:material:$compose_version")
    implementation ("androidx.activity:activity-compose:1.4.0")
    implementation ("androidx.navigation:navigation-compose:$nav_version")

    //Tests
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_version")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:$compose_version")

    //Accompanist
    val accompanist_version: String by rootProject.extra
    implementation("com.google.accompanist:accompanist-pager:$accompanist_version")
    implementation("com.google.accompanist:accompanist-flowlayout:$accompanist_version")
    implementation("com.google.accompanist:accompanist-placeholder:$accompanist_version")

    //Flinger
    val flinger_version: String by rootProject.extra
    implementation ("com.github.iamjosephmj:Flinger:$flinger_version")

    //Hilt
    val hilt_version: String by rootProject.extra
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-compiler:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //Coil
    implementation("io.coil-kt:coil-compose:2.1.0")
}