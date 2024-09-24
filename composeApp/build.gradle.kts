import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
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
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // DI
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            // Coroutines
            implementation(libs.kotlinx.coroutines.android)

            // Ktor
            implementation(libs.ktor.client.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.datetime)

            // DI
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Navigation
            implementation(libs.navigation.compose)

            // Serialization
            implementation(libs.serialization.json)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // DataStore
            implementation(libs.datastore.preferences)
            implementation(libs.datastore)

            // Networking
            implementation(libs.bundles.ktor)

            // Image loading
            implementation(libs.kamel.image)

            // ViewModel
            implementation(libs.lifecycle.viewmodel)

            // Room
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
        }
        iosMain.dependencies {
            // Ktor
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.marossolutions.skeletonmultiplatform"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.marossolutions.skeletonmultiplatform"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

buildkonfig {
    packageName = "com.marossolutions.skeletonmultiplatform"

    val localProperties =
        Properties().apply {
            val propsFile = rootProject.file("local.properties")
            if (propsFile.exists()) {
                load(propsFile.inputStream())
            }
        }

    val apiNinjaApiKey = "API_NINJA_API_KEY"
    val airlabsApiKey = "AIRLABS_API_KEY"
    val geoApifyMapsKey = "GEOAPIFY_MAPS_KEY"
    val apiNinjaBaseUrl = "API_NINJA_BASE_URL"
    val airlabsBaseUrl = "AIRLABS_BASE_URL"

    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = apiNinjaApiKey,
            value = localProperties[apiNinjaApiKey]?.toString() ?: error("API_NINJA_API_KEY is missing"),
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = airlabsApiKey,
            value = localProperties[airlabsApiKey]?.toString() ?: error("AIRLABS_API_KEY is missing"),
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = geoApifyMapsKey,
            value = localProperties[geoApifyMapsKey]?.toString() ?:error("GEOAPIFY_MAPS_KEY is missing"),
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = apiNinjaBaseUrl,
            value = localProperties[apiNinjaBaseUrl]?.toString() ?: error("API_NINJA_BASE_URL is missing"),
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = airlabsBaseUrl,
            value = localProperties[airlabsBaseUrl]?.toString() ?: error("AIRLABS_BASE_URL is missing"),
        )
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    ksp(libs.room.compiler)
}

