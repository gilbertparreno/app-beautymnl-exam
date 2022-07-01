package com.beautymnl.exam.core.extensions

import androidx.annotation.DrawableRes
import com.beautymnl.exam.R
import com.beautymnl.exam.core.enums.InsetContainerType
import com.beautymnl.exam.core.enums.InsetContainerType.*

object InsetHelper {

    @DrawableRes
    fun getInsetBackground(insetContainerType: InsetContainerType): Int {
        return when (insetContainerType) {
            ROUNDED -> R.drawable.background_inset_rounded_corners
            ROUNDED_TOP -> R.drawable.background_inset_top_rounded_corners
            ROUNDED_TOP_RIGHT -> R.drawable.background_inset_top_right_rounded_corners
            ROUNDED_TOP_LEFT -> R.drawable.background_inset_top_left_rounded_corners
            ROUNDED_BOTTOM -> R.drawable.background_inset_bottom_rounded_corners
            ROUNDED_BOTTOM_RIGHT -> R.drawable.background_inset_bottom_right_rounded_corners
            ROUNDED_BOTTOM_LEFT -> R.drawable.background_inset_bottom_left_rounded_corners
            ROUNDED_RIGHT -> R.drawable.background_inset_right_rounded_corners
            ROUNDED_LEFT -> R.drawable.background_inset_left_rounded_corners
            DEFAULT -> R.drawable.background_inset_default
        }
    }

    @DrawableRes
    fun getInsetForeground(insetContainerType: InsetContainerType): Int {
        return when (insetContainerType) {
            ROUNDED -> R.drawable.ripple_default_rounded_11dp
            ROUNDED_TOP -> R.drawable.ripple_top_rounded_11dp
            ROUNDED_TOP_RIGHT -> R.drawable.ripple_top_right_rounded_11dp
            ROUNDED_TOP_LEFT -> R.drawable.ripple_top_left_rounded_11dp
            ROUNDED_BOTTOM -> R.drawable.ripple_bottom_rounded_11dp
            ROUNDED_BOTTOM_RIGHT -> R.drawable.ripple_bottom_right_rounded_11dp
            ROUNDED_BOTTOM_LEFT -> R.drawable.ripple_bottom_left_rounded_11dp
            ROUNDED_RIGHT -> R.drawable.ripple_right_rounded_11dp
            ROUNDED_LEFT -> R.drawable.ripple_left_rounded_11dp
            DEFAULT -> R.drawable.ripple_default
        }
    }
}