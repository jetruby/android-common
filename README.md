JETRUBY ANDROID COMMON
======================
[![](https://jitpack.io/v/jetruby/android-common.svg)](https://jitpack.io/#jetruby/android-common)

#### USAGE 

Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### PROJECT MODULES:

#### :rxcountries
 - api to request countries list with common methods
 
 ```gradle
 dependencies {
     implementation 'com.github.jetruby.android-common:rxcountries:{last_version}'
 }
 ```
 
#### :flagsres
- lib with flags image assets and common get methods

```gradle
dependencies {
    implementation 'com.github.jetruby.android-common:flagsres:{last_version}'
}
```
