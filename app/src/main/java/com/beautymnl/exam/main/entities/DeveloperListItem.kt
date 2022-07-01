package com.beautymnl.exam.main.entities

import com.beautymnl.exam.core.enums.InsetContainerType
import com.beautymnl.exam.core.enums.InsetContainerType.DEFAULT
import com.beautymnl.exam.core.extensions.allTrue
import com.beautymnl.exam.core.interfaces.InsetItem

data class DeveloperListItem(
    val name: String,
    val photoUrl: String,
    val email: String,
    val phoneNumber: String,
    val company: String,
    var isExpanded: Boolean = false,
    override var insetContainerType: InsetContainerType = DEFAULT
) : InsetItem {

    override fun equals(other: Any?): Boolean {
        return (other as? DeveloperListItem)?.let {
            allTrue(
                it.name == name,
                it.photoUrl == photoUrl,
                it.phoneNumber == phoneNumber,
                it.company == company,
                it.insetContainerType == insetContainerType,
                it.isExpanded == isExpanded
            )
        } ?: false
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + photoUrl.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + company.hashCode()
        result = 31 * result + isExpanded.hashCode()
        result = 31 * result + insetContainerType.hashCode()
        return result
    }
}