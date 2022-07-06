package com.beautymnl.exam.main.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import coil.transform.RoundedCornersTransformation
import com.beautymnl.exam.R
import com.beautymnl.exam.core.architecture.BaseListAdapter
import com.beautymnl.exam.core.entities.DeveloperListItem
import com.beautymnl.exam.core.enums.InsetContainerType.*
import com.beautymnl.exam.core.extensions.*
import com.beautymnl.exam.main.adapters.callbacks.DevelopersListDiffUtil
import kotlinx.android.synthetic.main.view_developer_item.view.*

class DevelopersListAdapter(
    override var items: MutableList<DeveloperListItem> = mutableListOf(),
    private val onEditItemPressed: ((developer: DeveloperListItem) -> Unit)? = null,
    private val onDeleteItemPressed: ((developer: DeveloperListItem) -> Unit)? = null,
    private val onOptionVisibilityChanged: ((isVisible: Boolean) -> Unit)? = null
) : BaseListAdapter<DeveloperListItem>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.view_developer_item))
    }

    override fun onBindViewHolder(view: View, item: DeveloperListItem, position: Int) {
        with(view) {
            developerName.text = item.name
            developerEmail.text = item.email
            developerCompany.text = item.company
            developerPhone.text = item.phoneNumber
            developerPhoto.load(item.photoUrl) {
                transformations(RoundedCornersTransformation(100f))
            }

            divider.goneIf {
                anyTrue(
                    item.insetContainerType == ROUNDED_BOTTOM,
                    item.insetContainerType == ROUNDED
                )
            }

            delete.goneIf { !item.showOptions }

            setInset(item.insetContainerType)

            setDebounceClickListener {
                if (items.any { it.showOptions }) {
                    hideOptions()
                    onOptionVisibilityChanged?.invoke(items.any { it.showOptions })
                } else {
                    onEditItemPressed?.invoke(item)
                }
            }
            setOnLongClickListener {
                setListItems(items.map { it.copy(showOptions = !it.showOptions) })
                onOptionVisibilityChanged?.invoke(items.any { it.showOptions })
                return@setOnLongClickListener true
            }
            delete.setDebounceClickListener {
                onDeleteItemPressed?.invoke(item)
            }

            when (item.insetContainerType) {
                ROUNDED_BOTTOM -> {
                    if (items.any { it.showOptions }) {
                        marginBottomDp(R.dimen.spacing_m)
                    } else {
                        marginBottom(88)
                    }
                    marginTop(0)
                }
                ROUNDED_TOP -> {
                    marginTopDp(R.dimen.spacing_m)
                    marginBottom(0)
                }
                else -> {
                    marginBottom(0)
                    marginTop(0)
                }
            }
        }
    }

    fun setListItems(items: List<DeveloperListItem>) {
        val diffResult = DiffUtil.calculateDiff(DevelopersListDiffUtil(this.items, items))
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addDeveloperToList(item: DeveloperListItem) {
        val index = this.items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            val newList = this.items.toMutableList().apply {
                set(
                    index,
                    get(index).copy(
                        name = item.name,
                        email = item.email,
                        phoneNumber = item.phoneNumber,
                        company = item.company
                    )
                )
                applyInset()
            }
            setListItems(newList)
        } else {
            val newList = this.items.toMutableList().apply {
                add(0, item.copy(insetContainerType = ROUNDED_TOP))
                getOrNull(1)?.let {
                    set(1, it.copy(insetContainerType = DEFAULT))
                }
            }
            setListItems(newList)
        }
    }

    fun removeItem(id: Int) {
        setListItems(
            items.filterNot { it.id == id }
                .map { it.copy() }
                .applyInset()
        )
    }

    fun hideOptions() {
        setListItems(items.map { it.copy(showOptions = false) })
    }
}