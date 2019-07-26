package com.hasankucuk.socialtextview.extensions

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import kotlin.math.roundToInt

class RoundedHighlightSpan(
    private val HIGHLIGHT_RADIUS: Int = 8,
    @ColorInt
    private val backgroundColor: Int = Color.YELLOW,
    @ColorInt
    private val textColor: Int = Color.BLACK
) : ReplacementSpan() {


    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fontMetrics: Paint.FontMetricsInt?
    ): Int {
        return paint.measureText(text, start, end).roundToInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        left: Float,
        top: Int,
        right: Int,
        bottom: Int,
        paint: Paint
    ) {
        val rectF = RectF(left, top.toFloat(), left + measureTextPaint(paint, text, start, end), bottom.toFloat())
        paint.color = backgroundColor
        canvas.drawRoundRect(rectF, HIGHLIGHT_RADIUS.toFloat(), HIGHLIGHT_RADIUS.toFloat(), paint)
        paint.color = textColor
        canvas.drawText(text!!, start, end, left, right.toFloat(), paint)
    }


    private fun measureTextPaint(paint: Paint, charSequence: CharSequence?, start: Int, end: Int): Float {
        return paint.measureText(charSequence, start, end)
    }

}