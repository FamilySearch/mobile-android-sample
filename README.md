# mobile-android-sample
This is a sample native android app that you can use as a starting point in creating your own mobile app that integrates with FamilySearch APIs.

For other sample apps and SDK information see https://familysearch.org/developers/libraries

### Tools required
In order to use this demo application you must first have installed:
* [Android Studio](https://developer.android.com/studio/index.html)
* Android SDK version 23 (Can be installed from within Android Studio, or with homebrew `brew install android-sdk`)
* [JDK7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
* An API key [issued by Family Search](https://familysearch.org/developers/). Place ths key inside of `AppKeys.java`

### Create AppKeys.java file
Create a file called `AppKeys.java` in the folder app/src/main/java/org/familysearch/sampleapp. Add a static String variable named `API_KEY`, with the value being your FamilySearch app key.

Example:
```java
package org.familysearch.sampleapp;

public class AppKeys {
    public static final String API_KEY = "your_api_key";
}
```

### Screen Shots
![screenshot](https://cloud.githubusercontent.com/assets/796795/15783372/19dca1c8-296b-11e6-9f8c-877574f01551.png)
![screenshot](https://cloud.githubusercontent.com/assets/796795/15783371/19d8d976-296b-11e6-8605-d45f77067ac0.png)
![screenshot](https://cloud.githubusercontent.com/assets/796795/15783373/19e719fa-296b-11e6-958f-a1ca2ef8cc80.png)
![screenshot](https://cloud.githubusercontent.com/assets/796795/15783374/19ea74c4-296b-11e6-8c3f-dc8cd57b974e.png)
