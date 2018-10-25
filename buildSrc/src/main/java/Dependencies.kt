import org.gradle.api.JavaVersion

object General {
  val sourceCompatibility = JavaVersion.VERSION_1_8
  val targetCompatibility = JavaVersion.VERSION_1_8

  const val TestInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Version {

  // project
  const val minSdk = 21
  const val targetSdk = 28
  const val buildToolsVersion = "28.0.3"
  const val compileSdkVersion = 28
  const val androidGradle = "3.2.1"

  // kotlin
  const val kotlin = "1.2.71" // https://kotlinlang.org/

  // serialization
  const val jackson = "2.9.7" //https://github.com/FasterXML/jackson-core

  // tests
  const val jUnit = "4.12"
  const val androidJUnit = "1.0.0"
  const val androidTestRunner = "1.0.0-rc01"
  const val espresso = "3.1.0-alpha4"
}

object Deps {
  const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"
  const val jacksonCore = "com.fasterxml.jackson.core:jackson-core:${Version.jackson}"

  // Tests
  const val jUnit = "junit:junit:${Version.jUnit}"
  const val androidJUnit = "androidx.test.ext:junit:${Version.androidJUnit}"
  const val androidTestRunner = "androidx.test:runner:${Version.androidTestRunner}"
  const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espresso}"
}
