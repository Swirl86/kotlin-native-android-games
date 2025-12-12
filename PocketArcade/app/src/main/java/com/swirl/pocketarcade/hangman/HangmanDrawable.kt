package com.swirl.pocketarcade.hangman

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import com.swirl.pocketarcade.hangman.model.HangmanPart

class HangmanDrawable : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        strokeWidth = 8f
    }

    private var parts: List<HangmanPart> = emptyList()

    fun setVisibleParts(parts: List<HangmanPart>) {
        this.parts = parts
        invalidateSelf()
    }

    override fun draw(canvas: Canvas) {
        val w = bounds.width()
        val h = bounds.height()

        for (part in parts) {
            when (part) {
                HangmanPart.BASE -> canvas.drawLine(w * 0.1f, h * 0.9f, w * 0.9f, h * 0.9f, paint)
                HangmanPart.POLE -> canvas.drawLine(w * 0.2f, h * 0.9f, w * 0.2f, h * 0.1f, paint)
                HangmanPart.SUPPORT -> canvas.drawLine(w * 0.3f, h * 0.1f, w * 0.2f, h * 0.3f, paint)
                HangmanPart.BEAM -> canvas.drawLine(w * 0.2f, h * 0.1f, w * 0.6f, h * 0.1f, paint)
                HangmanPart.ROPE -> canvas.drawLine(w * 0.6f, h * 0.1f, w * 0.6f, h * 0.2f, paint)
                HangmanPart.HEAD -> canvas.drawOval(w * 0.55f, h * 0.2f, w * 0.65f, h * 0.3f, paint)
                HangmanPart.BODY -> canvas.drawLine(w * 0.6f, h * 0.3f, w * 0.6f, h * 0.5f, paint)
                HangmanPart.LEFT_ARM -> canvas.drawLine(w * 0.6f, h * 0.35f, w * 0.55f, h * 0.45f, paint)
                HangmanPart.RIGHT_ARM -> canvas.drawLine(w * 0.6f, h * 0.35f, w * 0.65f, h * 0.45f, paint)
                HangmanPart.LEFT_LEG -> canvas.drawLine(w * 0.6f, h * 0.5f, w * 0.55f, h * 0.65f, paint)
                HangmanPart.RIGHT_LEG -> canvas.drawLine(w * 0.6f, h * 0.5f, w * 0.65f, h * 0.65f, paint)
                HangmanPart.FACE -> {
                    val facePaint = Paint(paint).apply { color = Color.RED }
                    canvas.drawCircle(w * 0.575f, h * 0.225f, w * 0.01f, facePaint)
                    canvas.drawCircle(w * 0.625f, h * 0.225f, w * 0.01f, facePaint)
                    canvas.drawCircle(w * 0.6f, h * 0.28f, w * 0.015f, facePaint)
                }
            }
        }
    }

    override fun setAlpha(alpha: Int) { paint.alpha = alpha }
    @Deprecated("Deprecated in Android")
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
    override fun setColorFilter(colorFilter: ColorFilter?) { paint.colorFilter = colorFilter }
}
