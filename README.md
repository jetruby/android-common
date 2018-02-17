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

#### :countries
 - api to request countries list with common methods
 
 ```gradle
 dependencies {
     implementation 'com.github.jetruby.android-common:countries:{last_version}'
 }
 ```
 
#### :flagassets
- lib with flags image assets and common get methods

```gradle
dependencies {
    implementation 'com.github.jetruby.android-common:flagassets:{last_version}'
}
```
