package com.hasankucuk.socialtextview.extensions

import android.graphics.RectF
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.TextView

class LinkedMovement : LinkMovementMethod() {
    private val touchBounds = RectF()
    private var pressedSpan: TouchableSpan? = null

    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressedSpan = getTouchedSpan(widget, buffer, event)
                if (pressedSpan != null) {
                    pressedSpan!!.setPressed(true)
                    Selection.setSelection(
                        buffer,
                        buffer.getSpanStart(pressedSpan),
                        buffer.getSpanEnd(pressedSpan)
                    )
                }
            }

            MotionEvent.ACTION_MOVE -> {
                val pressedSpan2 = getTouchedSpan(widget, buffer, event)
                if (pressedSpan != null && pressedSpan2 !== pressedSpan) {
                    pressedSpan!!.setPressed(false)
                    pressedSpan = null
                    Selection.removeSelection(buffer)
                }
            }

            else -> {
                if (pressedSpan != null) {
                    pressedSpan!!.setPressed(false)
                    super.onTouchEvent(widget, buffer, event)
                }
                pressedSpan = null
                Selection.removeSelection(buffer)
            }
        }
        return true
    }


    private fun getTouchedSpan(tv: TextView, span: Spannable, e: MotionEvent): TouchableSpan? {
        var x = e.x.toInt()
        var y = e.y.toInt()

        // Ignore padding
        x -= tv.totalPaddingLeft
        y -= tv.totalPaddingTop

        x += tv.scrollX
        y += tv.scrollY

        val layout = tv.layout
        val touchedLine = layout.getLineForVertical(y)
        val touchOffset = layout.getOffsetForHorizontal(touchedLine, x.toFloat())

        touchBounds.left = layout.getLineLeft(touchedLine)
        touchBounds.top = layout.getLineTop(touchedLine).toFloat()
        touchBounds.right = layout.getLineRight(touchedLine)
        touchBounds.bottom = layout.getLineBottom(touchedLine).toFloat()

        var touchSpan: TouchableSpan? = null
        if (touchBounds.contains(x.toFloat(), y.toFloat())) {
            val spans = span.getSpans(touchOffset, touchOffset, TouchableSpan::class.java)
            touchSpan = if (spans.isNotEmpty()) spans[0] else null
        }

        return touchSpan
    }

    companion object {
        private var instance: LinkedMovement? = null

        @Synchronized
        fun getInstance(): LinkedMovement {
            if (instance == null) {
                instance = LinkedMovement()
            }
            return instance as LinkedMovement
        }
    }

}