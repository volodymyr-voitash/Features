plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "contact-details"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":contact:contact-domain"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.precompose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.kermit)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.voitash.contact_details"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
