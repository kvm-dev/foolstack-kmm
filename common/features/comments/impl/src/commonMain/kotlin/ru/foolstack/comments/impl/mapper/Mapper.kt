package ru.foolstack.comments.impl.mapper

import ru.foolstack.comments.api.model.MaterialCommentRequestDomain
import ru.foolstack.comments.api.model.MaterialCommentResponseDomain
import ru.foolstack.comments.impl.model.MaterialCommentRequest
import ru.foolstack.comments.impl.model.MaterialCommentResponse

class Mapper {

    fun mapToRequest(request: MaterialCommentRequestDomain): MaterialCommentRequest{
        return MaterialCommentRequest(
            comment = request.comment,
            materialId = request.materialId
        )
    }

    fun mapToResponse(response: MaterialCommentResponse): MaterialCommentResponseDomain{
        return MaterialCommentResponseDomain(
            success = response.success,
            errorMsg = response.errorMsg
        )
    }
}