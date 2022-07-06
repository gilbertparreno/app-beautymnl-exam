package com.beautymnl.exam.core.base

import android.content.Context
import android.graphics.Rect
import android.view.ViewTreeObserver
import android.widget.FrameLayout

interface BaseFragmentViewKeyboardDelegate {
    fun onKeyboardHeightChanged(height: Int)
}

abstract class BaseFragmentView(context: Context) : FrameLayout(context) {

    protected var keyboardDelegate: BaseFragmentViewKeyboardDelegate? = null

    private var previousKeyboardHeight = Int.MIN_VALUE
    private val bottomNavHeight by lazy {
        val resourceId = resources.getIdentifier(
            "navigation_bar_height",
            "dimen",
            "android"
        )
        resources.getDimensionPixelSize(resourceId);
    }
    private var keyboardListener = ViewTreeObserver.OnGlobalLayoutListener {
        val rect = Rect()
        rootView.getWindowVisibleDisplayFrame(rect)
        val heightDiff = (rootView.bottom - rect.bottom) - bottomNavHeight
        if (heightDiff != previousKeyboardHeight) {
            previousKeyboardHeight = heightDiff
            keyboardDelegate?.onKeyboardHeightChanged(heightDiff)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener(keyboardListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnGlobalLayoutListener(keyboardListener)
    }
}