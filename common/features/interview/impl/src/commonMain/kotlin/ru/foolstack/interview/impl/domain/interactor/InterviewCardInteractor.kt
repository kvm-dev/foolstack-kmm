package ru.foolstack.interview.impl.domain.interactor

import ru.foolstack.comments.api.domain.usecase.SendMaterialCommentUseCase
import ru.foolstack.comments.api.model.MaterialCommentRequestDomain
import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase

class InterviewCardInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    getMaterialsUseCase: GetMaterialsUseCase,
    private val sendMaterialCommentUseCase: SendMaterialCommentUseCase){
    val materialState = getMaterialsUseCase.materialsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun sendComment(materialId: Int, comment: String){
        sendMaterialCommentUseCase.sendComment(MaterialCommentRequestDomain(materialId = materialId, comment = comment))
    }

}