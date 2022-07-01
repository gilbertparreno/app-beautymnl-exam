package com.beautymnl.exam.main.adapters.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.beautymnl.exam.main.entities.DeveloperListItem

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
        return oldItems[oldItemPosition].email == newItems[newItemPosition].email
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}