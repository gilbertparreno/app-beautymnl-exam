package com.beautymnl.exam.core.entities

import androidx.annotation.Keep
import com.beautymnl.exam.core.enums.InsetContainerType
import com.beautymnl.exam.core.enums.InsetContainerType.DEFAULT
import com.beautymnl.exam.core.interfaces.InsetItem
import com.beautymnl.exam.core.networking.entities.Developer
import java.io.Serializable

@Keep
data class DeveloperListItem(
    val id: Int,
    val name: String,
    val photoUrl: String? = null,
    val email: String,
    val phoneNumber: String,
    val company: String,
    var showOptions: Boolean = false,
    override var insetContainerType: InsetContainerType = DEFAULT
) : InsetItem, Serializable {

    fun toDeveloper(): Developer {
        return Developer(
            id,
            name,
            photoUrl,
            email,
            phoneNumber,
            company
        )
    }
}