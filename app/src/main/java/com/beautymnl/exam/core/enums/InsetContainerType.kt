package com.beautymnl.exam.core.enums

enum class InsetContainerType(val value: Int) {
    DEFAULT(0),
    ROUNDED(1),
    ROUNDED_TOP(2),
    ROUNDED_TOP_RIGHT(3),
    ROUNDED_TOP_LEFT(4),
    ROUNDED_BOTTOM(5),
    ROUNDED_BOTTOM_RIGHT(6),
    ROUNDED_BOTTOM_LEFT(7),
    ROUNDED_RIGHT(8),
    ROUNDED_LEFT(9);

    companion object {
        fun getInsetType(value: Int): InsetContainerType {
            return values().firstOrNull {
                it.value == value
            } ?: DEFAULT
        }
    }
}