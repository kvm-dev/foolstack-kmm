package ru.foolstack.authorization.impl.model.response
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class IsUserExistResponse(
    @SerialName("success") var success: Boolean = false,
    @SerialName("errorMsg") var errorMsg: String

)