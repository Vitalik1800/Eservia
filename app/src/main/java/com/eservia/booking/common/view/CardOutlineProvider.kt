package com.eservia.booking.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import com.eservia.booking.util.ViewUtil

@SuppressLint("ObsoleteSdkInt", "SupportAnnotationUsage")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CardOutlineProvider(val context: Context, private var paddingX: Int,
                          private var paddingY: Int, private var yShift: Int, private var cornerRadius: Int) : ViewOutlineProvider() {

    constructor(context: Context) : this(context, DEFAULT_PADDING_X,
            DEFAULT_PADDING_Y, DEFAULT_SHIFT, DEFAULT_CORNER_RADIUS)

    companion object {
        const val DEFAULT_PADDING_X = 6
        const val DEFAULT_PADDING_Y = 8
        const val DEFAULT_SHIFT = -4
        const val DEFAULT_CORNER_RADIUS = 5
    }

    private val rect: Rect = Rect()

    override fun getOutline(view: View?, outline: Outline?) {
        view?.background?.copyBounds(rect)
        rect.scale(context, paddingX, paddingY)
        rect.offset(0, ViewUtil.dpToPixel(context, yShift).toInt())
        outline?.setRoundRect(rect, ViewUtil.dpToPixel(context, cornerRadius))
    }
}

private fun Rect.scale(context: Context, paddingX: Int, paddingY: Int) {

    val newWidth = width() - ViewUtil.dpToPixel(context, paddingX)
    val newHeight = height() - ViewUtil.dpToPixel(context, paddingY)
    val deltaX = (width() - newWidth) / 2
    val deltaY = (height() - newHeight) / 2

    set((left + deltaX).toInt(), (top + deltaY).toInt(), (right - deltaX).toInt(), (bottom - deltaY).toInt())
}
