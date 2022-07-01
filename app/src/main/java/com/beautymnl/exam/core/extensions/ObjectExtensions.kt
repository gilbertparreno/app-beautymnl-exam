package com.beautymnl.exam.core.extensions

import android.os.Handler
import android.os.Looper

fun runDelayed(delayMillis: Long, action: () -> Unit) {
    Handler(Looper.getMainLooper())
        .postDelayed(
            Runnable(action),
            delayMillis
        )
}