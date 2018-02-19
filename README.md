JETRUBY ANDROID COMMON
======================
[![](https://jitpack.io/v/jetruby/android-common.svg)](https://jitpack.io/#jetruby/android-common)

#### USAGE 

We are using [jitpack](https://jitpack.io/docs/) for repo publishing.

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
     implementation 'com.github.jetruby.android-common:countries:{latest_version}'
 }
 ```
 
#### :flagassets
   - lib with flags image assets and common get methods
   
   ```gradle
   dependencies {
       implementation 'com.github.jetruby.android-common:flag-assets:{latest_version}'
   }
   ```

#### :mapper-factory & :mapper-factory-processor

In our projects we are trying to use clean-architecture pattern, 
so we need object mapping between layers. For object mapping we use
[Mapstruct](http://mapstruct.org/).
 This is helper lib for generate simple mappers and access them with generated factory.
 
```gradle
dependencies {
    // mapstruct dependencies
    implementation "org.mapstruct:mapstruct-jdk8:{version}"
    annotationProcessor "org.mapstruct:mapstruct-processor:{version}"
    
    // ObjMapper interface extends io.reactivex.functions.Function so we need rxjava2 dependency
    implementation "io.reactivex.rxjava2:rxjava:{version}"
    
    implementation 'com.github.jetruby.android-common:mapper-factory:{last_version}'
    // annotation processot for generating ObjMapperFactory class
    annotationProcessor 'com.github.jetruby.android-common:mapper-factory-processor:{latest_version}'
}
```