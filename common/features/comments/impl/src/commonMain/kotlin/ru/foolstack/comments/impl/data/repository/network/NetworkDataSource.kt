package ru.foolstack.comments.impl.data.repository.network

import ru.foolstack.comments.api.model.MaterialCommentRequestDomain
import ru.foolstack.comments.api.model.MaterialCommentResponseDomain
import ru.foolstack.comments.impl.mapper.Mapper

class NetworkDataSource(private val api: CommentsApi, private val mapper: Mapper){

    suspend fun sendMaterialComment(request: MaterialCommentRequestDomain, userToken: String): MaterialCommentResponseDomain {
        return mapper.mapToResponse(api.sendMaterialComment(request = mapper.mapToRequest(request), userToken = userToken))
    }
}