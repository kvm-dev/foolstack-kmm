package ru.foolstack.interview.impl.domain.usecase

import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.impl.data.repository.MaterialsRepository

class GetMaterialsUseCaseImpl(private val repository: MaterialsRepository):GetMaterialsUseCase {
    override suspend fun getMaterials(fromLocal: Boolean): MaterialsDomain {
        return if(fromLocal){
           repository.getMaterialsFromLocal()
        }
        else{
             repository.getMaterialsFromServer()
        }
    }
}