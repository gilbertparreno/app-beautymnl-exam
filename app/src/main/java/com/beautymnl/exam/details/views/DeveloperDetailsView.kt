package com.beautymnl.exam.details.views

import android.animation.ValueAnimator
import android.content.Context
import android.telephony.PhoneNumberUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.util.PatternsCompat
import androidx.core.widget.doAfterTextChanged
import cn.pedant.SweetAlert.SweetAlertDialog
import com.beautymnl.exam.R
import com.beautymnl.exam.core.base.BaseFragmentView
import com.beautymnl.exam.core.base.BaseFragmentViewKeyboardDelegate
import com.beautymnl.exam.core.entities.DeveloperListItem
import com.beautymnl.exam.core.extensions.*
import com.beautymnl.exam.core.helpers.DialogHelper
import com.beautymnl.exam.core.utils.DEFAULT_DIALOG_KEY
import com.beautymnl.exam.details.enums.DetailsViewType
import kotlinx.android.synthetic.main.fragment_developer_details.view.*
import kotlinx.android.synthetic.main.view_app_bar.view.*
import java.lang.ref.WeakReference

interface DeveloperDetailsViewDelegate {
    fun onBackPressed()
    fun onContinue(
        fullName: String,
        email: String,
        phoneNumber: String,
        company: String
    )
}

class DeveloperDetailsView(context: Context) : BaseFragmentView(context),
    BaseFragmentViewKeyboardDelegate {

    var delegate: DeveloperDetailsViewDelegate? = null

    private val dialogs = mutableMapOf<String, WeakReference<SweetAlertDialog>>()

    init {
        inflate(context, R.layout.fragment_developer_details, this)
        keyboardDelegate = this

        continueButton.setDebounceClickListener {
            val fullName = fullNameInputLayout.getText() ?: ""
            val email = emailInputLayout.getText() ?: ""
            val phoneNumber = phoneNumberInputLayout.getText() ?: ""
            val company = companyInputLayout.getText() ?: ""
            delegate?.onContinue(
                fullName,
                email,
                "+639$phoneNumber",
                company
            )
        }

        fullNameInputLayout.editText?.doAfterTextChanged {
            setContinueButtonEnable()
        }
        emailInputLayout.editText?.doAfterTextChanged {
            setContinueButtonEnable()
        }
        phoneNumberInputLayout.editText?.doAfterTextChanged {
            setContinueButtonEnable()
        }
        companyInputLayout.editText?.doAfterTextChanged {
            setContinueButtonEnable()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        dialogs.values.map { it.get()?.dismiss() }
        dialogs.clear()
    }

    fun setTitle(detailsViewType: DetailsViewType) {
        toolbar.apply {
            title = if (detailsViewType == DetailsViewType.ADD) {
                getString(R.string.add_developer)
            } else {
                getString(R.string.edit_developer)
            }
            navigationIcon = getCompatDrawable(R.drawable.ic_toolbar_back)
            setNavigationOnClickListener {
                delegate?.onBackPressed()
            }
        }
    }

    fun setDetails(developerListItem: DeveloperListItem) {
        fullNameInputLayout.setText(developerListItem.name)
        emailInputLayout.setText(developerListItem.email)
        phoneNumberInputLayout.setText(developerListItem.phoneNumber.replace("+639", ""))
        companyInputLayout.setText(developerListItem.company)

        setContinueButtonEnable()
    }

    private fun setContinueButtonEnable() {
        val isValidPhoneNumber = phoneNumberInputLayout.editText?.text?.let {
            if (it.length == 9) {
                PhoneNumberUtils.isGlobalPhoneNumber(it.toString())
            } else {
                false
            }
        } ?: false
        val isValidEmail = emailInputLayout.editText?.text?.let {
            PatternsCompat.EMAIL_ADDRESS.matcher(it).matches()
        } ?: false
        continueButton.isEnabled = allTrue(
            fullNameInputLayout.editText?.text?.isNotEmpty() == true,
            companyInputLayout.editText?.text?.isNotEmpty() == true,
            isValidPhoneNumber,
            isValidEmail
        )
    }

    // BaseFragmentViewKeyboardDelegate

    override fun onKeyboardHeightChanged(height: Int) {
        val guideEnd = (keyboardGuideline.layoutParams as ConstraintLayout.LayoutParams)
            .guideEnd
        val valueAnimator = ValueAnimator.ofInt(guideEnd, height)
        valueAnimator.duration = 300
        valueAnimator.addUpdateListener {
            keyboardGuideline.setGuidelineEnd(it.animatedValue as Int)
        }
        valueAnimator.start()
    }

    fun showProgressDialog() {
        DialogHelper.showDialog(
            context = context,
            titleText = getString(R.string.please_wait),
            alertType = SweetAlertDialog.PROGRESS_TYPE,
            cancellable = false
        ).also {
            dialogs[DEFAULT_DIALOG_KEY] = WeakReference(it)
        }
    }

    fun showSuccessDialog(message: String, onDismiss: (() -> Unit)? = null) {
        dialogs[DEFAULT_DIALOG_KEY]?.get()?.apply {
            DialogHelper.showDialog(
                this,
                alertType = SweetAlertDialog.SUCCESS_TYPE,
                contentText = message,
                cancellable = false,
                autoDismiss = true,
                onDismiss = {
                    runDelayed(300) {
                        onDismiss?.invoke()
                    }
                }
            )
        }
    }

    fun showErrorDialog(message: String?, onDismiss: (() -> Unit)? = null) {
        dialogs[DEFAULT_DIALOG_KEY]?.get()?.apply {
            DialogHelper.showDialog(
                this,
                alertType = SweetAlertDialog.ERROR_TYPE,
                contentText = message ?: getString(R.string.generic_error_message),
                cancellable = false,
                autoDismiss = true,
                onDismiss = {
                    dialogs.remove(DEFAULT_DIALOG_KEY)
                    onDismiss?.invoke()
                }
            )
        }
    }
}