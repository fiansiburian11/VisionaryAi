//plugins {
//    alias(libs.plugins.android.application)
//    id("com.google.gms.google-services")  version "4.3.15"
//
//
//}
//
//
//android {
//    namespace = "com.aigemini"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.aigemini"
//        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    buildFeatures {
//        buildConfig = true
//    }
//}
//
//dependencies {
//
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//    implementation(libs.generativeai)
//    implementation(libs.dexter)
//    implementation(libs.playserviceslocation)
//    implementation(libs.exifinterface)
//    implementation(libs.lottie)
//    implementation(libs.guava)
//    implementation(libs.reactive.streams)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//
//    //auth firebase
//    implementation("com.google.firebase:firebase-auth:22.1.1")
//    implementation ("com.google.android.gms:play-services-auth:20.6.0")
//
//
//}

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.aigemini"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aigemini"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.generativeai)
    implementation(libs.dexter)
    implementation(libs.playserviceslocation)
    implementation(libs.exifinterface)
    implementation(libs.lottie)
    implementation(libs.guava)
    implementation(libs.reactive.streams)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.google.android.gms:play-services-auth:20.6.0")

}
