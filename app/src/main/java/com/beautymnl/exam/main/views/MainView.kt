package com.beautymnl.exam.main.views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.beautymnl.exam.R
import com.beautymnl.exam.core.base.BaseFragmentView
import com.beautymnl.exam.core.entities.DeveloperListItem
import com.beautymnl.exam.core.extensions.fadeInView
import com.beautymnl.exam.core.extensions.fadeOutView
import com.beautymnl.exam.core.extensions.getString
import com.beautymnl.exam.core.extensions.setDebounceClickListener
import com.beautymnl.exam.core.helpers.DialogHelper
import com.beautymnl.exam.core.utils.DEFAULT_DIALOG_KEY
import com.beautymnl.exam.main.adapters.DevelopersListAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.lang.ref.WeakReference

interface MainViewDelegate {
    fun onEditDeveloperPressed(item: DeveloperListItem)
    fun onDeleteDeveloperPressed(item: DeveloperListItem)
    fun onAddDeveloperPressed()
}

class MainView(context: Context) : BaseFragmentView(context) {

    var delegate: MainViewDelegate? = null

    private val dialogs = mutableMapOf<String, WeakReference<SweetAlertDialog>>()

    private val adapter = DevelopersListAdapter(
        onEditItemPressed = {
            delegate?.onEditDeveloperPressed(it)
        },
        onDeleteItemPressed = {
            delegate?.onDeleteDeveloperPressed(it)
        },
        onOptionVisibilityChanged = { isVisible ->
            if (isVisible) {
                fabAdd.fadeOutView()
            } else {
                fabAdd.fadeInView()
            }
        }
    )

    init {
        inflate(context, R.layout.fragment_main, this)

        developersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainView.adapter
        }
        fabAdd.setDebounceClickListener {
            delegate?.onAddDeveloperPressed()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        dialogs.values.map { it.get()?.dismiss() }
        dialogs.clear()
    }

    fun setDevelopersList(items: List<DeveloperListItem>) {
        adapter.setListItems(items)
    }

    fun addDeveloperToList(items: DeveloperListItem) {
        adapter.addDeveloperToList(items)
    }

    fun removeDeveloperFromList(id: Int) {
        adapter.removeItem(id)
    }

    fun isAdapterOptionsVisible(): Boolean {
        return adapter.items.any { it.showOptions }
    }

    fun hideAdapterOptions() {
        adapter.hideOptions()
        fabAdd.fadeInView()
    }

    fun showConfirmDeleteDialog(developerName: String, onConfirm: (() -> Unit)? = null) {
        DialogHelper.showDialog(
            context = context,
            titleText = getString(R.string.dialog_remove_developer_title),
            contentText = getString(R.string.dialog_remove_developer_message).format(developerName),
            confirmText = getString(R.string.yes),
            onConfirm = { dialog ->
                onConfirm?.invoke()
                dialogs[DEFAULT_DIALOG_KEY] = WeakReference(dialog)
            },
            alertType = SweetAlertDialog.WARNING_TYPE
        )
    }

    fun showLoading() {
        dialogs[DEFAULT_DIALOG_KEY]?.get()?.apply {
            DialogHelper.showDialog(
                dialog = this,
                titleText = getString(R.string.please_wait),
                alertType = SweetAlertDialog.PROGRESS_TYPE,
                cancellable = false
            )
        }
    }

    fun showSuccessDialog(onDismiss: (() -> Unit)?) {
        dialogs[DEFAULT_DIALOG_KEY]?.get()?.apply {
            DialogHelper.showDialog(
                this,
                alertType = SweetAlertDialog.SUCCESS_TYPE,
                contentText = getString(R.string.delete_success_message),
                cancellable = false,
                autoDismiss = true,
                onDismiss = {
                    onDismiss?.invoke()
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