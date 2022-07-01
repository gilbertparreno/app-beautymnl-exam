package com.beautymnl.exam.main.views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.beautymnl.exam.R
import com.beautymnl.exam.core.base.BaseFragmentView
import com.beautymnl.exam.main.adapters.DevelopersListAdapter
import com.beautymnl.exam.main.entities.DeveloperListItem
import kotlinx.android.synthetic.main.fragment_main.view.*

interface MainViewDelegate {
    fun onItemPressed(item: DeveloperListItem)
}

class MainView(context: Context) : BaseFragmentView(context) {

    var delegate: MainViewDelegate? = null

    private val adapter = DevelopersListAdapter {
        delegate?.onItemPressed(it)
    }

    init {
        inflate(context, R.layout.fragment_main, this)
        developersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainView.adapter
        }
    }

    fun setDevelopersList(items: List<DeveloperListItem>) {
        adapter.setListItems(items)
    }
}