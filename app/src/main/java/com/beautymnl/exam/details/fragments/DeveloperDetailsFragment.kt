package com.beautymnl.exam.details.fragments

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.beautymnl.exam.R
import com.beautymnl.exam.core.base.BaseFragment
import com.beautymnl.exam.core.entities.TaskStatus
import com.beautymnl.exam.core.extensions.safePopBackStack
import com.beautymnl.exam.details.enums.DetailsViewType
import com.beautymnl.exam.details.viewModels.DeveloperDetailsViewModel
import com.beautymnl.exam.details.views.DeveloperDetailsView
import com.beautymnl.exam.details.views.DeveloperDetailsViewDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeveloperDetailsFragment : BaseFragment<DeveloperDetailsView>(),
    DeveloperDetailsViewDelegate {

    private val developerItemArg: DeveloperDetailsFragmentArgs by navArgs()
    private val viewModel: DeveloperDetailsViewModel by viewModels()

    override fun onCreateView(context: Context, savedInstanceState: Bundle?): DeveloperDetailsView {
        return DeveloperDetailsView(context).also {
            it.delegate = this
        }
    }

    override fun onViewCreated(contentView: DeveloperDetailsView, savedInstanceState: Bundle?) {
        developerItemArg.developerItem?.let {
            contentView.setDetails(it)
        }
        contentView.setTitle(developerItemArg.detailsViewType)
    }

    override fun observeChanges() {
        viewModel.updateDeveloperEvent.observe(this) {
            when (it) {
                is TaskStatus.Loading -> contentView.showProgressDialog()
                is TaskStatus.SuccessWithResult -> {
                    setFragmentResult(
                        "update_developer",
                        bundleOf("item" to it.result.toDeveloperListItem())
                    )
                    contentView.showSuccessDialog(getString(R.string.update_success_message)) {
                        safePopBackStack()
                    }
                }
                is TaskStatus.FailureWithException -> contentView.showErrorDialog(it.error.message)
            }
        }
        viewModel.addDeveloperEvent.observe(this) {
            when (it) {
                is TaskStatus.Loading -> contentView.showProgressDialog()
                is TaskStatus.SuccessWithResult -> {
                    setFragmentResult(
                        "add_developer",
                        bundleOf("item" to it.result.toDeveloperListItem())
                    )
                    contentView.showSuccessDialog(getString(R.string.add_success_message)) {
                        safePopBackStack()
                    }
                }
                is TaskStatus.FailureWithException -> contentView.showErrorDialog(it.error.message)
            }
        }
    }

    // DeveloperDetailsViewDelegate

    override fun onBackPressed() {
        findNavController().popBackStack()
    }

    override fun onContinue(fullName: String, email: String, phoneNumber: String, company: String) {
        when (developerItemArg.detailsViewType) {
            DetailsViewType.EDIT -> {
                developerItemArg.developerItem?.toDeveloper()?.let {
                    viewModel.updateDetails(
                        it.copy(
                            name = fullName,
                            email = email,
                            phoneNumber = phoneNumber,
                            company = company
                        )
                    )
                }

            }
            DetailsViewType.ADD -> {
                viewModel.addDeveloper(fullName, email, phoneNumber, company)
            }
        }
    }
}