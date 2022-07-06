package com.beautymnl.exam.core.helpers

import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog
import com.beautymnl.exam.R
import com.beautymnl.exam.core.extensions.getCompatColor
import com.beautymnl.exam.core.extensions.runDelayed

object DialogHelper {

    fun showDialog(
        dialog: SweetAlertDialog,
        titleText: String? = null,
        contentText: String? = null,
        confirmText: String? = null,
        onConfirm: ((dialog: SweetAlertDialog) -> Unit)? = null,
        alertType: Int,
        cancellable: Boolean = true,
        autoDismiss: Boolean = false,
        autoDismissDuration: Long = 1500L,
        onDismiss: (() -> Unit)? = null
    ): SweetAlertDialog {
        return dialog.apply {
            changeAlertType(alertType)
            setTitleText(titleText ?: "")
            setContentText(contentText ?: "")
            confirmText?.let {
                setConfirmText(it)
            } ?: run {
                hideConfirmButton()
            }
            setConfirmClickListener {
                onConfirm?.invoke(it)
            }
            if (alertType == SweetAlertDialog.PROGRESS_TYPE) {
                progressHelper.barColor = context.getCompatColor(R.color.purple_500)
            }
            setCancelable(cancellable)

            if (autoDismiss) {
                runDelayed(autoDismissDuration) {
                    dismissWithAnimation()
                }
            }
            setOnDismissListener { onDismiss?.invoke() }
        }
    }

    fun showDialog(
        context: Context,
        titleText: String? = null,
        contentText: String? = null,
        confirmText: String? = null,
        onConfirm: ((dialog: SweetAlertDialog) -> Unit)? = null,
        alertType: Int,
        cancellable: Boolean = true,
        showInstantly: Boolean = true,
        onDismiss: (() -> Unit)? = null
    ): SweetAlertDialog {
        val dialog = SweetAlertDialog(context, alertType).apply {
            changeAlertType(alertType)
            setTitleText(titleText ?: "")
            setContentText(contentText ?: "")
            confirmText?.let {
                setConfirmText(it)
            } ?: run {
                hideConfirmButton()
            }
            setConfirmClickListener {
                onConfirm?.invoke(it)
            }
            setCancelable(cancellable)

            if (showInstantly) {
                show()
            }

            setOnDismissListener { onDismiss?.invoke() }
        }
        return dialog
    }
}