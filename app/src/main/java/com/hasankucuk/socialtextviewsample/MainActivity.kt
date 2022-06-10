package com.hasankucuk.socialtextviewsample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hasankucuk.socialtextview.SocialTextView
import com.hasankucuk.socialtextview.model.LinkedType
import com.hasankucuk.socialtextview.model.LinkedType.*
import com.hasankucuk.socialtextviewsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mentions: MutableList<String> = arrayListOf()
        mentions.add("@hasankucuk")
        //socialTextView.setLinkedMention(mentions)

        val hashtags: MutableList<String> = arrayListOf()
        hashtags.add("#developed")
        // socialTextView.setLinkedHashtag(hashtags)

        val highlightText: MutableList<String> = arrayListOf()
        highlightText.add("project")
        binding.socialTextView.setHighlightText(highlightText)

        binding.socialTextView.setLinkClickListener(object : SocialTextView.LinkClickListener {
            override fun onLinkClicked(linkType: LinkedType, matchedText: String) {
                when (linkType) {
                    TEXT -> {
                        Toast.makeText(
                            this@MainActivity,
                            "You are clicked type -> TEXT and value ->$matchedText",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    HIGHLITH -> {}
                    HASHTAG -> {
                        Toast.makeText(
                            this@MainActivity,
                            "You are clicked type -> HASHTAG and value ->$matchedText",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    MENTION -> {
                        Toast.makeText(
                            this@MainActivity,
                            "You are clicked type -> MENTION and value ->$matchedText",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    URL -> {
                        Toast.makeText(
                            this@MainActivity,
                            "You are clicked type -> URL and value ->$matchedText",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    EMAIL -> {
                        Toast.makeText(
                            this@MainActivity,
                            "You are clicked type -> EMAIL and value ->$matchedText",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    PHONE -> {
                        Toast.makeText(
                            this@MainActivity,
                            "You are clicked type -> PHONE and value ->$matchedText",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}
