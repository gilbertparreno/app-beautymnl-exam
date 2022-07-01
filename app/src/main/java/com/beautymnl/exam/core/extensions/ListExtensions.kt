package com.beautymnl.exam.core.extensions

import com.beautymnl.exam.core.enums.InsetContainerType
import com.beautymnl.exam.core.enums.InsetContainerType.*
import com.beautymnl.exam.core.interfaces.InsetItem

fun <T : InsetItem> List<T>.applyInset(): List<T> {
    return toMutableList().apply {
        if (isNotEmpty()) {
            if (count() == 1) {
                this[0] = this[0].also { it.insetContainerType = InsetContainerType.ROUNDED }
            } else {
                val top = indexOfLast { it.insetContainerType == ROUNDED_TOP }
                val bottom = indexOfFirst { it.insetContainerType == ROUNDED_BOTTOM }
                if (top > 0 || (bottom < lastIndex && bottom != -1)) {
                    forEachIndexed { index, item ->
                        this[index] = item.also { it.insetContainerType = DEFAULT }
                    }
                }
                this[0] = this[0].also { it.insetContainerType = ROUNDED_TOP }
                this[lastIndex] = this[lastIndex].also { it.insetContainerType = ROUNDED_BOTTOM }
            }
        }
    }
}