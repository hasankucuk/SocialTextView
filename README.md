# SocialTextView
A simple custom Android TextView that highlights content such as Mention, Hashtag, Phone, Email and Url.


<img src="https://raw.githubusercontent.com/hasankucuk/SocialTextView/master/art/1.png"/>



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
To capture click events
```kotlin
        socialTextView.setLinkClickListener(object : SocialTextView.LinkClickListener {
            override fun onLinkClicked(linkType: LinkedType, matchedText: String) {

                when (linkType) {
                    TEXT -> {
                        Toast.makeText(this@MainActivity, "You are clicked type -> TEXT and value ->$matchedText", Toast.LENGTH_SHORT).show()
                    }
                    HASHTAG -> {
                        Toast.makeText(this@MainActivity, "You are clicked type -> HASHTAG and value ->$matchedText", Toast.LENGTH_SHORT).show()
                    }
                    MENTION -> {
                        Toast.makeText(this@MainActivity, "You are clicked type -> MENTION and value ->$matchedText", Toast.LENGTH_SHORT).show()
                    }
                    URL -> {
                        Toast.makeText(this@MainActivity, "You are clicked type -> URL and value ->$matchedText", Toast.LENGTH_SHORT).show()
                    }
                    EMAIL -> {
                        Toast.makeText(this@MainActivity, "You are clicked type -> EMAIL and value ->$matchedText", Toast.LENGTH_SHORT).show()
                    }
                    PHONE -> {
                        Toast.makeText(this@MainActivity, "You are clicked type -> PHONE and value ->$matchedText", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
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


Attribute | Type | Summary
:---: | :---: | ---
`app:linkType` | `int` | Sets flags for which types of links the text should show. i.e: "hashtag|mention|email|phone|url".
`app:normalTextColor` | `int` | Sets the text color of a normal text color.
`app:hashtagColor` | `color` | Sets the text color of a hashtag link in the text.
`app:mentionColor` | `color` | Sets the text color of a mention link in the text.
`app:phoneColor` | `color` | Sets the text color of a phone number link in the text.
`app:emailColor` | `color` | Sets the text color of an email link in the text.
`app:urlColor` | `color` | Sets the text color of a web url link in the text.
`app:selectedColor` | `color` | Sets the text color of a selected link in the text.


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


