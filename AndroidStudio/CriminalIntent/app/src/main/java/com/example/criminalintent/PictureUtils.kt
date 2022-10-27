package com.example.criminalintent

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Build
import android.view.WindowInsets
import kotlin.math.roundToInt

fun getScaledBitmap(path: String, activity: Activity): Bitmap {
    val wm = activity.windowManager
    val width: Int
    val height: Int

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        val windowInsets: WindowInsets = windowMetrics.windowInsets

        val insets = windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())
        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom

        val b = windowMetrics.bounds
        width = b.width() - insetsWidth
        height = b.height() - insetsHeight
    } else {
        val size = Point()

        @Suppress("DEPRECATION")
        val display = wm.defaultDisplay // deprecated in API 30

        @Suppress("DEPRECATION")
        display?.getSize(size) // deprecated in API 30
        width = size.x
        height = size.y
    }

    return getScaledBitmap(path, width, height)
}

fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
    // Чтение размеров изображения на диске
    var options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)
    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()
    // Выясняем, на сколько нужно уменьшить
    var inSampleSize = 1
    if (srcHeight > destHeight || srcWidth > destWidth) {
        val heightScale = srcHeight / destHeight
        val widthScale = srcWidth / destWidth
        val sampleScale = if (heightScale > widthScale) {
            heightScale
        } else {
            widthScale
        }
        inSampleSize = sampleScale.roundToInt()
    }
    options = BitmapFactory.Options()
    options.inSampleSize = inSampleSize
    // Чтение и создание окончательного растрового изображения
    return BitmapFactory.decodeFile(path, options)
}