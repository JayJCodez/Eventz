plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.optionsmenupractice"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.optionsmenupractice"
        minSdk = 24
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

    buildFeatures{
        viewBinding = true
    }


    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.volley)
        implementation(libs.androidx.preference)
        implementation(libs.androidx.junit.ktx)
        implementation(libs.androidx.runner)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        implementation("com.squareup.okhttp3:okhttp:4.9.1")
        implementation("com.github.bumptech.glide:glide:4.12.0")
        annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
        implementation ("com.google.android.material:material:1.4.0")
        testImplementation("org.mockito:mockito-inline:4.8.0")

        androidTestImplementation ("androidx.test:runner:1.4.0")
        implementation("androidx.cardview:cardview:1.0.0")

        implementation("androidx.gridlayout:gridlayout:1.0.0")

        val fragment_version = "1.6.2"
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        // For control over item selection of both touch and mouse driven selection
        implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
        testImplementation ("junit:junit:4.13.2")
        androidTestImplementation ("androidx.test.ext:junit:1.1.3")
        androidTestImplementation("org.mockito:mockito-android:3.9.0")
        androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
        androidTestImplementation("androidx.test:rules:1.4.0")
        // Java language implementation
        implementation("androidx.fragment:fragment:$fragment_version")
        // Kotlin
        implementation("androidx.fragment:fragment-ktx:$fragment_version")

        testImplementation("com.google.truth:truth:1.0.1")
    }

}
dependencies {
    implementation(libs.androidx.espresso.intents)
    implementation(libs.androidx.espresso.contrib)
    implementation(libs.androidx.fragment.testing)
}
