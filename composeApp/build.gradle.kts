import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    jvmToolchain(17)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    compilerOptions {
        languageVersion.set(KOTLIN_1_9)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(project(":shared"))
            implementation(project(":contact:features:contact-list"))
            implementation(project(":contact:features:contact-details"))
            implementation(project(":contact:contact-domain"))
            implementation(project(":contact:contact-data"))
            implementation(project(":contact:contact-api"))
            implementation(project(":contact:contact-database"))
            implementation(libs.kermit)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.room.runtime)
            api(libs.precompose)
        }
    }
}

android {
    namespace = "com.voitash.features"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.voitash.features"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.koin.android)
        debugImplementation(libs.compose.ui.tooling)
    }
}
