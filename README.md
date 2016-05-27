# mobile-android-sample
Sample mobile native android app.

### Tools required
In order to use this demo application you must first have installed:
* [Android Studio](https://developer.android.com/studio/index.html)
* Android SDK version 23 (Can be installed from within Android Studio, or with homebrew `brew install android-sdk`)
* [JDK7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
* An API key [issued by Family Search](https://familysearch.org/developers/). Place ths key inside of `AppKeys.java`

### Create AppKeys.java file
In the root of the `sampleApp` folder, add a new java and name if `AppKeys.java`.
In here, add a static String variable named `API_KEY` and add your api key from Family search.

Example:
```java
package org.familysearch.sampleapp;

public class AppKeys {
    public static final String API_KEY = "your_api_key";
}
```
