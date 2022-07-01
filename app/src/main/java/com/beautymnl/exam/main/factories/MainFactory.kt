package com.beautymnl.exam.main.factories

import com.beautymnl.exam.core.extensions.applyInset
import com.beautymnl.exam.core.networking.entities.Developer
import com.beautymnl.exam.main.entities.DeveloperListItem

object MainFactory {

    fun convertToDeveloperListItem(developers: List<Developer>): List<DeveloperListItem> {
        return developers.map {
            DeveloperListItem(
                it.name,
                it.photoUrl,
                it.email,
                it.phoneNumber,
                it.company
            )
        }.applyInset()
    }
}