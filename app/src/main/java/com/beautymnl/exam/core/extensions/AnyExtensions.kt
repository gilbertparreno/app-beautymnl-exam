package com.beautymnl.exam.core.extensions

fun allTrue(vararg booleans: Boolean): Boolean {
    return booleans.all { it }
}

fun anyTrue(vararg booleans: Boolean): Boolean {
    return booleans.any { it }
}