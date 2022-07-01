package com.beautymnl.exam.main.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.beautymnl.exam.core.base.BaseFragment
import com.beautymnl.exam.core.entities.TaskStatus
import com.beautymnl.exam.main.entities.DeveloperListItem
import com.beautymnl.exam.main.viewModels.MainViewModel
import com.beautymnl.exam.main.views.MainView
import com.beautymnl.exam.main.views.MainViewDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainView>(), MainViewDelegate {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(context: Context, savedInstanceState: Bundle?): MainView {
        return MainView(context).also {
            it.delegate = this
        }
    }

    override fun onViewCreated(contentView: MainView, savedInstanceState: Bundle?) {
        viewModel.getDevelopers()
    }

    override fun observeChanges() {
        viewModel.developersListEvent.observe(this) {
            when (it) {
                is TaskStatus.Loading -> {}
                is TaskStatus.SuccessWithResult -> {
                    contentView.setDevelopersList(items = it.result)
                }
            }
        }
    }

    // MainViewDelegate

    override fun onItemPressed(item: DeveloperListItem) {

    }
}