package com.beautymnl.exam.main.adapters.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.beautymnl.exam.core.entities.DeveloperListItem
import com.beautymnl.exam.core.extensions.allTrue

class DevelopersListDiffUtil(
    private val oldItems: List<DeveloperListItem>,
    private val newItems: List<DeveloperListItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.count()
    }

    override fun getNewListSize(): Int {
        return newItems.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newItems[newItemPosition]
        val oldItem = oldItems[oldItemPosition]
        return allTrue(
            oldItem.id == newItem.id,
            oldItem.insetContainerType == newItem.insetContainerType
        )
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newItems[newItemPosition]
        val oldItem = oldItems[oldItemPosition]
        return allTrue(
            oldItem.id == newItem.id,
            oldItem.showOptions == newItem.showOptions,
            oldItem.name == newItem.name,
            oldItem.insetContainerType == newItem.insetContainerType,
            oldItem.phoneNumber == newItem.phoneNumber,
            oldItem.company == newItem.company,
            oldItem.email == newItem.email,
            oldItem.photoUrl == newItem.photoUrl
        )
    }
}