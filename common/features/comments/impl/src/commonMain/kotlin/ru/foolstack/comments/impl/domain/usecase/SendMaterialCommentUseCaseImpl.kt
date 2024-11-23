package ru.foolstack.comments.impl.domain.usecase

import ru.foolstack.comments.api.domain.usecase.SendMaterialCommentUseCase
import ru.foolstack.comments.api.model.MaterialCommentRequestDomain
import ru.foolstack.comments.api.model.MaterialCommentResponseDomain
import ru.foolstack.comments.impl.data.repository.CommentsRepository

class SendMaterialCommentUseCaseImpl(private val repository: CommentsRepository):SendMaterialCommentUseCase {
    override suspend fun sendComment(request: MaterialCommentRequestDomain): MaterialCommentResponseDomain {
        return repository.sendMaterialComment(request)
    }
}