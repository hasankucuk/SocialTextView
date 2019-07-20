# SocialTextView
A simple custom Android TextView that highlights content such as Mention, Hashtag, Phone, Email and Url.


<img src="https://raw.githubusercontent.com/hasankucuk/SocialTextView/master/art/1.jpg"/>



## Usage


```xml
    <com.hasankucuk.socialtextview.SocialTextView
        android:id="@+id/socialTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="15dp"
        android:text="This project was #developed by @hasankucuk as #opensource. \n
hasann.kucuk@yandex.ru \n
https://medium.com/@hasann.kucuk \n
hasan@gmail.com \n
+1 123456789"
        app:emailColor="#FF9E80"
        app:hashtagColor="#82B1FF"
        app:linkType="mention|hashtag|phone|email|url"
        app:mentionColor="#BCBCCF"
        app:normalTextColor="#18181F"
        app:phoneColor="#03A9F4"
        app:urlColor="#8BC34A" />

```



only for linking of the mention you specify
```kotlin
      val mentions: MutableList<String> = arrayListOf()
        mentions.add("@hasankucuk")
        socialTextView.setLinkedMention(mentions)
```
only for linking of the hashtag you specify
```kotlin
     val hashtags: MutableList<String> = arrayListOf()
        hashtags.add("#developed")
        socialTextView.setLinkedHashtag(hashtags)
```

## Setup
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
   implementation 'com.github.hasankucuk:SocialTextView:1.0.0'
}
```


License
--------


    Copyright 2019 Hasan Küçük

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


