plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.kotlin.kapt)
	alias(libs.plugins.parcelize)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt.android) apply false
}

android {
	namespace = "com.reggya.github"
	compileSdk = 35
	
	defaultConfig {
		applicationId = "com.reggya.github"
		minSdk = 24
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"
		this.buildConfigField("String", "TOKEN", "${properties["apiKey"]}")
		this.buildConfigField ("String", "BASE_URL", "${properties["baseURL"]}")
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
		buildConfig = true
		viewBinding = true
		dataBinding = true
	}
}

dependencies {
	
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.navigation.runtime.android)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
	implementation(libs.androidx.navigation.compose)
	
	//Retrofit
	implementation (libs.retrofit)
	implementation (libs.converter.gson)
	implementation (libs.logging.interceptor)
	
	//Dagger Hilt
	implementation (libs.hilt.android)
	kapt (libs.hilt.compiler)
	
	//Pagination
	implementation(libs.androidx.paging.compose)
	implementation (libs.androidx.paging.runtime)
	
	//room
	implementation(libs.androidx.room.runtime)
	annotationProcessor(libs.androidx.room.compiler)
	ksp(libs.androidx.room.compiler.v252)
	implementation(libs.androidx.room.ktx)
	
	//ViewModel
	implementation (libs.androidx.activity.ktx)
	implementation(libs.androidx.hilt.navigation.compose)
	implementation (libs.androidx.lifecycle.viewmodel.ktx)
	
	//Chucker
	debugImplementation(libs.chucker)
	releaseImplementation(libs.chucker.no.op)
	
	implementation(libs.coil.compose)
}