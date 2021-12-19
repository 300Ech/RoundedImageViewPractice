package com.evertschavez.dogs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView

class RoundedImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {
    enum class CornerType(val value: Int) {
        CORNER_NONE(0),
        CORNER_TOP_LEFT(1),
        CORNER_TOP_RIGHT(2),
        CORNER_BOTTOM_RIGHT(4),
        CORNER_BOTTOM_LEFT(8),
        CORNER_ALL(15)
    }

    private val cornerRect = RectF()
    private val path: Path = Path()
    private var cornerRadius = 0
    private var roundedCorners = 0

    init {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView)
        cornerRadius =
            attributeSet.getDimensionPixelSize(R.styleable.RoundedImageView_cornerRadius, 0)
        roundedCorners = attributeSet.getInt(
            R.styleable.RoundedImageView_roundedCorners,
            CornerType.CORNER_NONE.value
        )
        attributeSet.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        if (!path.isEmpty) {
            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setPath()
    }

    fun setCornerRadius(radius: Int) {
        cornerRadius = radius
        setPath()
        invalidate()
    }

    fun getRadius(): Int {
        return cornerRadius
    }

    fun setRoundedCorners(corners: Int) {
        roundedCorners = corners
        setPath()
        invalidate()
    }

    fun getRoundedCorners(): Int {
        return roundedCorners
    }

    private fun setPath() {
        path.rewind()

        if (cornerRadius >= 1f && roundedCorners != CornerType.CORNER_NONE.value) {
            val twoRadius = (cornerRadius * 2).toFloat()

            cornerRect[-cornerRadius.toFloat(), -cornerRadius.toFloat(), cornerRadius.toFloat()] =
                cornerRadius.toFloat()

            if (isRounded(CornerType.CORNER_TOP_LEFT.value))
                setRoundedCorner(0f, 0f, 180f)
            else path.moveTo(0f, 0f)

            if (isRounded(CornerType.CORNER_TOP_RIGHT.value))
                setRoundedCorner(width - twoRadius, 0f, 270f)
            else path.lineTo(width.toFloat(), 0f)

            if (isRounded(CornerType.CORNER_BOTTOM_RIGHT.value))
                setRoundedCorner(width - twoRadius, height - twoRadius, 0f)
            else path.lineTo(width.toFloat(), height.toFloat())

            if (isRounded(CornerType.CORNER_BOTTOM_LEFT.value))
                setRoundedCorner(0f, height - twoRadius, 90f)
            else path.lineTo(0f, height.toFloat())

            path.close()
        }
    }

    private fun setRoundedCorner(newLeft: Float, newTop: Float, startAngle: Float) {
        cornerRect.offsetTo(newLeft, newTop)
        path.arcTo(cornerRect, startAngle, 90f)
    }

    private fun isRounded(corner: Int): Boolean = roundedCorners and corner == corner
}