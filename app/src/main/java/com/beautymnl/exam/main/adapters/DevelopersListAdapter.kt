package com.beautymnl.exam.main.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import coil.transform.RoundedCornersTransformation
import com.beautymnl.exam.R
import com.beautymnl.exam.core.architecture.BaseListAdapter
import com.beautymnl.exam.core.enums.InsetContainerType.ROUNDED
import com.beautymnl.exam.core.enums.InsetContainerType.ROUNDED_BOTTOM
import com.beautymnl.exam.core.extensions.*
import com.beautymnl.exam.main.adapters.callbacks.DevelopersListDiffUtil
import com.beautymnl.exam.main.entities.DeveloperListItem
import kotlinx.android.synthetic.main.view_developer_item.view.*

class DevelopersListAdapter(
    override var items: List<DeveloperListItem> = listOf(),
    private val onItemPressed: ((developer: DeveloperListItem) -> Unit)? = null
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

            listOf(developerCompany, developerPhone).forEach {
                it.goneIf { !item.isExpanded }
            }

            setInset(item.insetContainerType)

            setDebounceClickListener {
                setListItems(
                    items.mapIndexed { index, oldItem ->
                        if (index == position) {
                            oldItem.copy(isExpanded = !oldItem.isExpanded)
                        } else {
                            oldItem.copy(isExpanded = false)
                        }
                    }
                )
            }
        }
    }

    fun setListItems(items: List<DeveloperListItem>) {
        val diffResult = DiffUtil.calculateDiff(DevelopersListDiffUtil(this.items, items))
        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }
}