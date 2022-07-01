package com.beautymnl.exam.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<V : BaseFragmentView> : Fragment() {

    lateinit var contentView: V

    abstract fun onCreateView(context: Context, savedInstanceState: Bundle?): V
    abstract fun onViewCreated(contentView: V, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return onCreateView(inflater.context, savedInstanceState).also {
            contentView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreated(contentView, savedInstanceState)
        observeChanges()
    }

    abstract fun observeChanges()
}