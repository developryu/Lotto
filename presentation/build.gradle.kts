plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.rhi.personal.lotto.presentation"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = rootProject.extra["sourceCompatibility"] as JavaVersion
        targetCompatibility = rootProject.extra["targetCompatibility"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.extra["jvmTarget"] as String
    }
    buildFeatures {
        compose = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":modules:QrScan"))

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Timber
    implementation(libs.timber)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.androidx.compiler)

    // orbit (mvi library)
    implementation(libs.orbit.core)
    implementation(libs.orbit.viewmodel)
    implementation(libs.orbit.compose)

    // Compose Navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // Lottie
    implementation(libs.lottie.compose)

    // kotlin reflect <mainNavClass.sealedSubclasses.forEach>
    implementation(libs.kotlin.reflect)

    // permission check
    implementation(libs.accompanist.permissions)

    // Widget
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)

    // DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Gson
    implementation(libs.gson)
}