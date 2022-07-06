package com.beautymnl.exam.core.extensions

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.SystemClock
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.beautymnl.exam.R
import com.beautymnl.exam.core.enums.InsetContainerType

fun View.dp(value: Int): Int = context.dp(value)

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

fun View.getString(@StringRes resId: Int) : String {
    return context.getString(resId)
}

fun View.getDimension(@DimenRes resId: Int) =
    context.resources.getDimension(resId).toInt()

fun View.marginTopDp(@DimenRes marginRes: Int) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        topMargin = getDimension(marginRes)
    }
}

fun View.marginBottomDp(@DimenRes marginRes: Int) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        bottomMargin = getDimension(marginRes)
    }
}

fun View.marginBottom(bottom: Int? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        bottom?.run { bottomMargin = dp(this) }
    }
}

fun View.marginTop(top: Int? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        top?.run { topMargin = dp(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

inline fun View.fadeOutView(
    duration: Long = 300,
    setGone: Boolean = true,
    crossinline endAction: () -> Unit = {}
) {
    this.animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction {
            if (setGone) {
                this.visibility = GONE
            }
            endAction.invoke()
        }.start()
}

fun View.fadeInView(duration: Long = 300, alpha: Float = 1f) {
    this.visibility = VISIBLE
    this.animate().alpha(alpha).setDuration(duration).start()
}