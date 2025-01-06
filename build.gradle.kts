
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2") // Tambahkan plugin google-services di sini
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false



}