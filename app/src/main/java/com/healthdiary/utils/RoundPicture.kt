package com.healthdiary.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RoundPicture : AppCompatImageView{
    private var paint: Paint? = null

    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        paint = Paint()
    }

    //    绘制圆形图片
    override fun onDraw(canvas: Canvas) {
        val drawable = drawable
        if (null != drawable) {
            val bitmap = (drawable as BitmapDrawable).bitmap
            val b = getCircleBitmap(bitmap, 14)
            val rectSrc = Rect(0, 0, b.width, b.height)
            val rectDest = Rect(0, 0, width, height)
            paint!!.reset()
            canvas.drawBitmap(b, rectSrc, rectDest, paint)
        } else {
            super.onDraw(canvas)
        }
    }

    //     获取圆形图片方法
    private fun getCircleBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        paint!!.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint!!.color = color
        val x = bitmap.width
        canvas.drawCircle((x / 2).toFloat(), (x / 2).toFloat(), (x / 2).toFloat(), paint!!)
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }
}