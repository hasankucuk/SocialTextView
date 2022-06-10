package com.hasankucuk.socialtextview.extensions

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan

abstract class TouchableSpan(
    private val normalTextColor: Int,
    private val pressedTextColor: Int,
    private val isUnderline: Boolean
) : ClickableSpan() {

    private var isPressed: Boolean = false


    override fun updateDrawState(paint: TextPaint) {
        super.updateDrawState(paint)
        /*     if (normalTextColor == Color.BLACK && !isUnderline)
                 return
             */
        val textColor = if (isPressed) pressedTextColor else normalTextColor
        paint.color = textColor
        paint.isUnderlineText = isUnderline
        paint.bgColor = Color.TRANSPARENT

    }

    internal fun setPressed(pressed: Boolean) {
        this.isPressed = pressed
    }
}