plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.carspotteropsc7312poe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.carspotteropsc7312poe"
        minSdk = 25
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    // Force Kotlin tasks to use JVM 1.8
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    // Force kapt to use JVM 1.8
    kapt {
        javacOptions {
            option("-source", "1.8")
            option("-target", "1.8")
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Firebase BoM (Bill of Materials) - ensures consistent Firebase versions
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")

    // Google Sign-In and Identity
    implementation("com.google.android.gms:play-services-auth:20.7.0")  // Updated to stable
    implementation("com.google.android.gms:play-services-identity:18.1.0")

    // Room Database with KTX and Annotation Processor (Kapt) for Room support
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // WorkManager for Background Sync
    implementation("androidx.work:work-runtime-ktx:2.8.0") // Latest stable version compatible with SDK 34

    // AndroidX Lifecycle ViewModel KTX for viewModelScope support
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // GSON for JSON Parsing
    implementation("com.google.code.gson:gson:2.10.1")

    // Google Location Services
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // Mapbox Maps SDK
    implementation("com.mapbox.maps:android:10.10.0")

    // Networking and Image Loading Libraries
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.squareup.picasso:picasso:2.71828")  // Latest stable release

    // Circle Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // AndroidX Core, AppCompat, Material Design, and ConstraintLayout
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test:rules:1.5.0")

    // Additional dependencies
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")  // Ensure Kotlin version matches your project's Kotlin version
}
