package com.beautymnl.exam.core.extensions

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.SystemClock
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.beautymnl.exam.R
import com.beautymnl.exam.core.enums.InsetContainerType

inline fun View.setDebounceClickListener(
    debounceTime: Long = 600L,
    crossinline action: () -> Unit
) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action.invoke()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.setInset(
    insetContainerType: InsetContainerType,
    applyForeground: Boolean = true,
    @ColorInt backgroundColor: Int? = null,
    @DrawableRes insetBackgroundRes: Int? = null,
    @DrawableRes insetForegroundRes: Int? = null
) {
    val backgroundRes = insetBackgroundRes ?: InsetHelper.getInsetBackground(insetContainerType)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val foregroundRes = insetForegroundRes ?: InsetHelper.getInsetForeground(insetContainerType)
        foreground = if (applyForeground)
            getCompatDrawable(foregroundRes)
        else null
    }

    background = getCompatDrawable(backgroundRes)
    background = (background as GradientDrawable).also {
        it.setColor(context.getCompatColor(R.color.white))
    }
}

fun View.getCompatDrawable(@DrawableRes resId: Int): Drawable? {
    return context.getCompatDrawable(resId)
}

fun View.goneIf(action: () -> Boolean) {
    visibility = if (action.invoke()) GONE else VISIBLE
}