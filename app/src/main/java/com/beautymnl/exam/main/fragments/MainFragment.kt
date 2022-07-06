package com.beautymnl.exam.main.fragments

import android.content.Context
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beautymnl.exam.R
import com.beautymnl.exam.core.base.BaseFragment
import com.beautymnl.exam.core.entities.DeveloperListItem
import com.beautymnl.exam.core.entities.TaskStatus
import com.beautymnl.exam.core.extensions.runDelayed
import com.beautymnl.exam.core.helpers.ToastHelper
import com.beautymnl.exam.details.enums.DetailsViewType.ADD
import com.beautymnl.exam.details.enums.DetailsViewType.EDIT
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
            viewModel.getDevelopers()
        }
    }

    override fun onViewCreated(contentView: MainView, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (contentView.isAdapterOptionsVisible()) {
                        contentView.hideAdapterOptions()
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
        )
        setUpFragmentResults()
    }

    override fun observeChanges() {
        viewModel.getDevelopersListEvent.observe(this) {
            when (it) {
                is TaskStatus.Loading -> {}
                is TaskStatus.SuccessWithResult -> {
                    contentView.setDevelopersList(it.result)
                }
                is TaskStatus.FailureWithException -> {
                    ToastHelper.showErrorToast(
                        requireContext(),
                        it.error.message ?: getString(R.string.generic_error_message)
                    )
                }
            }
        }
        viewModel.deleteDeveloperEvent.observe(this) {
            when (it) {
                is TaskStatus.Loading -> {
                    contentView.showLoading()
                }
                is TaskStatus.SuccessWithResult -> {
                    contentView.showSuccessDialog {
                        runDelayed(300) {
                            contentView.removeDeveloperFromList(it.result)
                        }
                    }
                }
                is TaskStatus.FailureWithException -> contentView.showErrorDialog(it.error.message)
            }
        }
    }

    // MainViewDelegate

    override fun onEditDeveloperPressed(item: DeveloperListItem) {
        findNavController().navigate(
            MainFragmentDirections.mainToDeveloperDetails(
                item,
                EDIT
            )
        )
    }

    override fun onDeleteDeveloperPressed(item: DeveloperListItem) {
        contentView.showConfirmDeleteDialog(item.name) {
            viewModel.deleteDeveloper(item.id)
        }
    }

    override fun onAddDeveloperPressed() {
        findNavController().navigate(
            MainFragmentDirections.mainToDeveloperDetails(
                detailsViewType = ADD,
                developerItem = null
            )
        )
    }

    private fun setUpFragmentResults() {
        setFragmentResultListener("add_developer") { _, bundle ->
            contentView.addDeveloperToList(bundle.get("item") as DeveloperListItem)
        }
        setFragmentResultListener("update_developer") { _, bundle ->
            contentView.addDeveloperToList(bundle.get("item") as DeveloperListItem)
        }
    }
}