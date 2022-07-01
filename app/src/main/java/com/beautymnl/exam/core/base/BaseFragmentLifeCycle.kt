package com.beautymnl.exam.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

abstract class BaseFragmentLifeCycle<VM : ViewModel, V : BaseFragmentView> : BaseFragment<V>(),
    LifecycleOwner {

    abstract fun observerChanges()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(contentView, savedInstanceState)
        observerChanges()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }
}