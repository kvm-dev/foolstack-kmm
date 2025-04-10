package ru.foolstack.interview.impl.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.impl.data.repository.MaterialsRepository
import ru.foolstack.utils.model.ResultState

class GetMaterialsUseCaseImpl(private val repository: MaterialsRepository):GetMaterialsUseCase {

    private val _materials = MutableStateFlow<ResultState<MaterialsDomain>>(
        ResultState.Idle)
    override val materialsState = _materials.asStateFlow()
    override suspend fun getMateialsByProfession(professionId: Int): MaterialsDomain {
        val localMaterials = repository.getMaterialsByProfession(professionId)
        _materials.tryEmit(ResultState.Loading)
        _materials.tryEmit(ResultState.Success(localMaterials))
        return localMaterials
    }


//    override suspend fun getMaterials(fromLocal: Boolean): MaterialsDomain {
//        _materials.tryEmit(ResultState.Loading)
//        return if(fromLocal){
//            val cachedMaterials = repository.getMaterialsFromLocal()
//            _materials.tryEmit(ResultState.Success(cachedMaterials))
//            cachedMaterials
//        }
//        else{
//            val responseMaterials = repository.getMaterialsFromServer()
//            _materials.tryEmit(ResultState.Success(responseMaterials))
//            responseMaterials
//        }
//    }
//
//    override suspend fun getMaterialsByProfession(professionId: Int, fromLocal: Boolean): MaterialsDomain {
//        _materials.tryEmit(ResultState.Loading)
//        return if(fromLocal){
//            val cachedMaterials = repository.getMaterialsFromLocal()
//            _materials.tryEmit(ResultState.Success(cachedMaterials))
//            cachedMaterials
//        }
//        else{
//            val responseMaterials = repository.getMaterialsByProfessionFromServer(professionId = professionId)
//            _materials.tryEmit(ResultState.Success(responseMaterials))
//            responseMaterials
//        }
//    }
}