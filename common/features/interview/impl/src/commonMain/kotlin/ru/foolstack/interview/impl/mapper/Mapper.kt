package ru.foolstack.interview.impl.mapper

import ru.foolstack.interview.api.model.MaterialDomain
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.impl.model.MaterialResponse
import ru.foolstack.interview.impl.model.MaterialsResponse
import ru.foolstack.storage.model.Material
import ru.foolstack.storage.model.Materials

class Mapper {

    fun mapFromResponseMaterialsToDomain(response:MaterialsResponse):MaterialsDomain{
        val materialsData = ArrayList<MaterialDomain>()
        response.materials.forEach { material->
            materialsData.add(mapFromResponseMaterialToDomain(material))
        }
        return MaterialsDomain(
            materials = materialsData,
            errorMsg = response.errorMsg
        )
    }

    private fun mapFromResponseMaterialToDomain(response: MaterialResponse):MaterialDomain{
        return MaterialDomain(
            materialId = response.materialId,
            materialName = response.materialName,
            materialText = response.materialText,
            materialPriority = response.materialPriority,
            knowledgeAreas = response.knowledgeAreas,
            subProfessions = response.subProfessions
        )
    }

    fun mapMaterialsDomain(response: Materials):MaterialsDomain{
        return MaterialsDomain(
            materials = mapMaterialDomain(response.materials),
            errorMsg = ""
        )
    }

    private fun mapMaterialDomain(response: List<Material>):List<MaterialDomain>{
        val materialsData = ArrayList<MaterialDomain>()
        response.forEach {
            material->
            materialsData.add((MaterialDomain(
                materialId = material.materialId,
                materialName = material.materialName,
                materialText = material.materialText,
                materialPriority = material.materialPriority,
                knowledgeAreas = material.knowledgeAreas,
                subProfessions = material.subProfessions
            )))
        }
        return materialsData
    }

    fun mapMaterials(response: MaterialsDomain):Materials{
        return Materials(
            materials = mapMaterial(response.materials)
        )
    }

    private fun mapMaterial(response: List<MaterialDomain>):List<Material>{
        val materialData = ArrayList<Material>()
        response.forEach { material ->
            materialData.add(Material(
                materialId = material.materialId,
                materialName = material.materialName,
                materialText = material.materialText,
                materialPriority = material.materialPriority,
                knowledgeAreas = material.knowledgeAreas,
                subProfessions = material.subProfessions
            ))
        }
        return materialData
    }



}