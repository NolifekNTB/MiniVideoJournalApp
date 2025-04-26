plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("app.cash.sqldelight") version "2.0.2"
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.minivideojournalapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.minivideojournalapp"
        minSdk = 28
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.example.minivideojournalapp")
        }
    }
}
dependencies {
    // Kotlin test
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation ("org.slf4j:slf4j-api:2.0.17")
    testImplementation ("org.slf4j:slf4j-simple:2.0.17")


    // Mocking
    testImplementation ("io.mockk:mockk:1.13.4")
    androidTestImplementation ("io.mockk:mockk:1.13.4")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    androidTestImplementation ("androidx.test:core:1.5.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    //SQLDelight
    implementation(libs.android.driver)
    implementation(libs.coroutines.extensions)

    //Media3 ExoPlayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.datasource.okhttp)

    //Camera API
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)

    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Request permission at runtime
    implementation (libs.accompanist.permissions)


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
}

