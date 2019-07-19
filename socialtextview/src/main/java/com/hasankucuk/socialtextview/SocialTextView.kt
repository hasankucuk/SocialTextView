package com.hasankucuk.socialtextview

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.hasankucuk.socialtextview.extensions.LinkedMovement
import com.hasankucuk.socialtextview.extensions.TouchableSpan
import com.hasankucuk.socialtextview.model.LinkItem
import com.hasankucuk.socialtextview.model.LinkedType

class SocialTextView(context: Context, attrs: AttributeSet? = null, def: Int = 0) :
    AppCompatTextView(context, attrs, def) {


    private var hashtagColor = Color.BLUE
    private var mentionColor = Color.YELLOW
    private var emailColor = Color.CYAN
    private var urlColor = Color.RED
    private var normalTextColor = Color.BLACK
    private var selectedColor = Color.GRAY
    private var isUnderline = false
    private  var linkedType: LinkedType

    init {
        movementMethod = LinkedMovement()

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.SocialTextView, def, def)

        hashtagColor = typedArray.getColor(R.styleable.SocialTextView_hashtagColor, Color.BLUE)
        mentionColor = typedArray.getColor(R.styleable.SocialTextView_mentionColor, Color.YELLOW)
        emailColor = typedArray.getColor(R.styleable.SocialTextView_emailColor, Color.CYAN)
        urlColor = typedArray.getColor(R.styleable.SocialTextView_urlColor, Color.RED)
        normalTextColor = typedArray.getColor(R.styleable.SocialTextView_normalTextColor, Color.BLACK)
        selectedColor = typedArray.getColor(R.styleable.SocialTextView_selectedColor, Color.GRAY)
        isUnderline = typedArray.getBoolean(R.styleable.SocialTextView_underLine, false)

        linkedType = LinkedType( 1)

        if (typedArray.hasValue(R.styleable.SocialTextView_android_text)) {
            setLinkText(typedArray.getText(R.styleable.SocialTextView_android_text))
        }

        typedArray.recycle()
    }

    private fun setLinkText(text: CharSequence?) {
        setText(addSocialMediaSpan(text))
    }

    private fun addSocialMediaSpan(text: CharSequence?): SpannableString {

        val items = collectLinkItemsFromText(text)
        val textSpan = SpannableString(text)
        for (item in items) {
            textSpan.setSpan(object : TouchableSpan(getColorByMode(item.mode), selectedColor, isUnderline) {
                override fun onClick(view: View) {
                    super.onClick(view)


                }
            }, item.start, item.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return textSpan
    }


    private fun getColorByMode(mode: Any): Int {

    }

    private fun collectLinkItemsFromText(text: CharSequence?): Set<LinkItem> {


    }

}