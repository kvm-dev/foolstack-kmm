package ru.foolstack.comments.api.domain.usecase

import ru.foolstack.comments.api.model.MaterialCommentRequestDomain
import ru.foolstack.comments.api.model.MaterialCommentResponseDomain

interface SendMaterialCommentUseCase {

    suspend fun sendComment(request: MaterialCommentRequestDomain): MaterialCommentResponseDomain
}