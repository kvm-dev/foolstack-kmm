package ru.foolstack.profile.impl.data.repository.network

object ProfileEndpoints {
    val getProfile: String
        get() = "profile/get-profile/"

    val updateName: String
        get() = "profile/update-name/"

    val updateEmail: String
        get() = "profile/update-email/"

    val updatePhoto: String
        get() = "profile/update-photo/"

    val deleteProfile: String
        get() = "profile/delete-profile/"
}