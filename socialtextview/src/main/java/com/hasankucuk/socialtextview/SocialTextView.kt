package com.hasankucuk.socialtextview

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.util.AttributeSet
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatTextView
import com.hasankucuk.socialtextview.extensions.LinkedMovement
import com.hasankucuk.socialtextview.extensions.TouchableSpan
import com.hasankucuk.socialtextview.model.LinkItem
import com.hasankucuk.socialtextview.model.LinkedType
import com.hasankucuk.socialtextview.model.LinkedType.*
import java.util.ArrayList
import java.util.HashSet
import java.util.regex.Matcher
import java.util.regex.Pattern

class SocialTextView(context: Context, attrs: AttributeSet? = null, def: Int = 0) :
    AppCompatTextView(context, attrs, def) {


    private var hashtagColor = Color.BLUE
    private var mentionColor = Color.YELLOW
    private var emailColor = Color.CYAN
    private var urlColor = Color.RED
    private var phoneColor = Color.GREEN
    private var normalTextColor = Color.BLACK
    private var selectedColor = Color.GRAY
    private var isUnderline = false
    private var linkedType: LinkedType

    private var linkedMentions: List<String> = ArrayList()
    private var isLinkedMention = false


    private var patternHashtag: Pattern? = null
    private var patternMention: Pattern? = null
    private var patternText: Pattern? = null
    private var patternLink: Pattern? = null

    var onLinkClickListener: OnLinkClickListener? = null

    interface OnLinkClickListener {
        fun onLinkClicked(linkType: Int, matchedText: String)
    }

    init {
        movementMethod = LinkedMovement()

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.SocialTextView, def, def)

        hashtagColor = typedArray.getColor(R.styleable.SocialTextView_hashtagColor, Color.BLUE)
        mentionColor = typedArray.getColor(R.styleable.SocialTextView_mentionColor, Color.YELLOW)
        emailColor = typedArray.getColor(R.styleable.SocialTextView_emailColor, Color.CYAN)
        urlColor = typedArray.getColor(R.styleable.SocialTextView_urlColor, Color.RED)
        phoneColor = typedArray.getColor(R.styleable.SocialTextView_phoneColor, Color.GREEN)
        normalTextColor = typedArray.getColor(R.styleable.SocialTextView_normalTextColor, Color.BLACK)
        selectedColor = typedArray.getColor(R.styleable.SocialTextView_selectedColor, Color.GRAY)
        isUnderline = typedArray.getBoolean(R.styleable.SocialTextView_underLine, false)

        linkedType = LinkedType.getType(typedArray.getInt(R.styleable.SocialTextView_linkType, LinkedType.TEXT.value))

        if (typedArray.hasValue(R.styleable.SocialTextView_android_text)) {
            setLinkText(typedArray.getText(R.styleable.SocialTextView_android_text))
        }

        typedArray.recycle()
    }

    private fun setLinkText(text: CharSequence?) {
        setText(addSocialMediaSpan(text))
    }

    override fun setHighlightColor(@ColorInt color: Int) {
        super.setHighlightColor(Color.TRANSPARENT)
    }


    fun setLinkedMention(linkedMentions: List<String>) {

        this.linkedMentions = linkedMentions
        isLinkedMention = true
        val temp = getText().toString()
        setText("")
        setText(addSocialMediaSpan(temp))
    }

    fun appendLinkText(text: String) {
        append(addSocialMediaSpan(text))
    }


    private fun addSocialMediaSpan(text: CharSequence?): SpannableString {

        val items = collectLinkItemsFromText(text.toString())
        val textSpan = SpannableString(text)
        for (item in items) {
            textSpan.setSpan(object :
                TouchableSpan(getColorByMode(LinkedType.getType(item.mode)), selectedColor, isUnderline) {
                override fun onClick(view: View) {
                    super.onClick(view)
                    if (onLinkClickListener != null) {
                        onLinkClickListener!!.onLinkClicked(item.mode, item.matched)
                    }

                }
            }, item.start, item.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return textSpan
    }


    private fun getColorByMode(type: LinkedType): Int = when (type) {
        HASHTAG -> hashtagColor
        MENTION -> mentionColor
        PHONE -> phoneColor
        URL -> urlColor
        EMAIL -> emailColor
        TEXT -> normalTextColor

        else -> throw IllegalArgumentException("Invalid Linked Type!")
    }


    private fun collectLinkItemsFromText(text: String): Set<LinkItem> {
        val items = HashSet<LinkItem>()
        var linkedText: String = text

        when (linkedType) {

            HASHTAG -> {

                linkedText = collectLinkItems(HASHTAG.value, items, hashtagPattern.matcher(linkedText), text)

            }
            MENTION -> {
                linkedText = collectLinkItems(MENTION.value, items, mentionPattern!!.matcher(linkedText), text)

            }
            URL -> {
                linkedText = collectLinkItems(URL.value, items, linkPattern!!.matcher(linkedText), text)

            }
            EMAIL -> {
                linkedText = collectLinkItems(EMAIL.value, items, Patterns.EMAIL_ADDRESS.matcher(linkedText), text)

            }
            PHONE -> {
                linkedText = collectLinkItems(PHONE.value, items, Patterns.PHONE.matcher(linkedText), text)

            }

            else -> {
                collectLinkItems(TEXT.value, items, standartText!!.matcher(linkedText), text)

            }
        }

        return items
    }

    private val standartText: Pattern?
        get() {
            if (patternText == null) {
                patternText = Pattern.compile("(?u)(?<![@])#?\\b\\w\\w+\\b")
            }
            return patternText
        }

    private val linkPattern: Pattern?
        get() {
            if (patternLink == null) {
                patternLink = Pattern.compile(
                    "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)"
                )
            }
            return patternLink
        }

    private val mentionPattern: Pattern?
        get() {
            if (patternMention == null) {
                patternMention = Pattern.compile("(?:^|\\s|$|[.])@[\\p{L}0-9_]*")
            }
            return patternMention
        }
    private val hashtagPattern: Pattern
        get() {
            if (patternHashtag == null) {
                patternHashtag = Pattern.compile("(?:^|\\s|$)#[\\p{L}0-9_]*")
            }
            return patternHashtag!!
        }

    private fun collectLinkItems(
        mode: Int, items: MutableCollection<LinkItem>,
        matcher: Matcher,
        text: String
    ): String {
        var text = text
        while (matcher.find()) {
            var matcherStart = matcher.start()
            var matchedText = matcher.group()

            if (matchedText.startsWith(" ")) {
                matcherStart += 1
                matchedText = matchedText.substring(1)
            }

            if (matchedText.contains("@") && linkedMentions.size != 0) {
                if (linkedMentions.contains(matchedText)) {
                    items.add(
                        LinkItem(
                            matchedText,
                            matcherStart,
                            matcher.end(),
                            mode
                        )
                    )
                }
            } else if (!isLinkedMention) {
                items.add(
                    LinkItem(
                        matchedText,
                        matcherStart,
                        matcher.end(),
                        mode
                    )
                )
            }
            text = text.replace(matchedText, addSpace(matchedText.length - 1))
        }
        return text
    }

    private fun addSpace(count: Int): String {
        var addSpace = " "
        for (i in 0 until count) {
            addSpace = "$addSpace "
        }
        return addSpace
    }


}