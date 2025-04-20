plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.recipefinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.recipefinder"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
}

dependencies {
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
        implementation("com.google.android.material:material:1.10.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.test:core:1.5.0")
        androidTestImplementation("androidx.test:runner:1.5.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")


        // Remove these if present:
        // implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
        // implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }

}
