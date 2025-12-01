plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    //firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.memorama"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.memorama"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        dataBinding = false
    }
    composeOptions{
        kotlinCompilerExtensionVersion = "2.0.0"
    }
}

dependencies {
    // --- Core Android ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // --- Room & DataStore ---
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.compiler) {
        exclude(group = "com.intellij", module = "annotations")
    }
    // Datastore (Preferencias y Core)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.protolite.well.known.types)

    // --- Coroutines ---
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // --- Compose & Material 3 ---
    implementation(platform(libs.androidx.compose.bom))

    // UI, Tooling y Foundation
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation)

    // Material 3
    implementation(libs.androidx.compose.material3)

    // Iconos Extendidos (CORREGIDO)
    // Nota: Si agregaste la linea al TOML usa: implementation(libs.androidx.material.icons.extended)
    // Si no, usa esta línea directa que funcionará con el BOM:
    implementation("androidx.compose.material:material-icons-extended")

    // --- Navegación y Ciclo de Vida ---
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // --- Firebase ---
    implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    implementation("com.google.firebase:firebase-analytics")

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
