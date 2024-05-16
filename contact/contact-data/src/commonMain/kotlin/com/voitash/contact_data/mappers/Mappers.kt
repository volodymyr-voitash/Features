package com.voitash.contact_data.mappers

import com.voitash.contact_api.model.ApiContact
import com.voitash.contact_api.model.Picture
import com.voitash.contact_database.contacts.DbContact
import com.voitash.contact_domain.model.Contact
import com.voitash.contact_domain.model.ContactWithoutId

fun DbContact.toContact(): Contact {
    return Contact(id = id, firstName = firstName, lastName = lastName, email = email, photo = photo)
}

fun Contact.toDbContact(): DbContact {
    return DbContact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        photo = photo
    )
}

fun ContactWithoutId.toDbContact(): DbContact {
    return DbContact(
        firstName = firstName,
        lastName = lastName,
        email = email,
        photo = photo
    )
}

fun ApiContact.toContact(): ContactWithoutId {
    return ContactWithoutId(
        firstName = name?.firstName,
        lastName = name?.lastName,
        email = email,
        photo = picture?.getPhoto()
    )
}

fun Picture.getPhoto(): String? {
    return when {
        medium != null -> medium
        large != null -> large
        else -> thumbnail
    }
}