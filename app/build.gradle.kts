import java.util.regex.Pattern.compile

plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.anew"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.anew"
        minSdk = 24
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation ("org.apache.commons:commons-collections4:4.4")
    implementation ("org.apache.commons:commons-lang3:3.9")
    implementation ("org.apache.commons:commons-compress:1.19")
    implementation ("commons-net:commons-net:3.6")
    implementation ("commons-io:commons-io:2.6")
    implementation ("org.apache.commons:commons-exec:1.3")
    implementation ("commons-codec:commons-codec:1.13")
    implementation ("org.apache.httpcomponents:httpclient:4.5.10")
    implementation ("commons-validator:commons-validator:1.6")
    implementation ("org.apache.commons:commons-math3:3.6.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
android {
    packagingOptions {
        exclude ("META-INF/DEPENDENCIES")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/LICENSE'")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/NOTICE.txt")
    }
}