package com.beautymnl.exam.core.networking.entities

import com.beautymnl.exam.core.entities.DeveloperListItem

data class Developer(
    val id: Int,
    val name: String,
    val photoUrl: String? = null,
    val email: String,
    val phoneNumber: String,
    val company: String
) {

    fun toDeveloperListItem(): DeveloperListItem {
        return DeveloperListItem(
            id,
            name,
            photoUrl,
            email,
            phoneNumber,
            company
        )
    }
}