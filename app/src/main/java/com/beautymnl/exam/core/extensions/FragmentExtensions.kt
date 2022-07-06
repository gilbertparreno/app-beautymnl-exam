package com.beautymnl.exam.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

fun Fragment.safePopBackStack() {
    lifecycleScope.launchWhenResumed {
        findNavController().popBackStack()
    }
}