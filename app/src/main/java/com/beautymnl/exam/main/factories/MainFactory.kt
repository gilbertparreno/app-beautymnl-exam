package com.beautymnl.exam.main.factories

import com.beautymnl.exam.core.entities.DeveloperListItem
import com.beautymnl.exam.core.extensions.applyInset
import com.beautymnl.exam.core.networking.entities.Developer

object MainFactory {

    fun convertToDeveloperListItem(developers: List<Developer>): List<DeveloperListItem> {
        return developers.map {
            DeveloperListItem(
                it.id,
                it.name,
                it.photoUrl,
                it.email,
                it.phoneNumber,
                it.company
            )
        }.applyInset()
    }
}