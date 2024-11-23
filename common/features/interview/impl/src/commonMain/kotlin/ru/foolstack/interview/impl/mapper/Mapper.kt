package ru.foolstack.interview.impl.mapper

import ru.foolstack.events.api.model.EventDomain
import ru.foolstack.events.api.model.EventSubDomain
import ru.foolstack.interview.api.model.KnowledgeAreaDomain
import ru.foolstack.interview.api.model.MaterialDomain
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.api.model.SubProfessionDomain
import ru.foolstack.interview.impl.model.MaterialResponse
import ru.foolstack.interview.impl.model.MaterialsResponse
import ru.foolstack.storage.model.KnowledgeArea
import ru.foolstack.storage.model.Material
import ru.foolstack.storage.model.Materials
import ru.foolstack.storage.model.ProfessionListItem
import ru.foolstack.ui.model.Chip
import ru.foolstack.ui.model.MaterialSectionItem

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
        val areas = ArrayList<KnowledgeAreaDomain>()
        val subProfessions = ArrayList<SubProfessionDomain>()
        response.knowledgeAreas.forEach { area->
            areas.add(KnowledgeAreaDomain(
                areaId = area.areaId,
                areaName = area.areaName
            ))
        }
        response.subProfessions.forEach { subProf->
            subProfessions.add(SubProfessionDomain(
                professionId = subProf.professionId,
                professionName = subProf.professionName
            ))
        }
        return MaterialDomain(
            materialId = response.materialId,
            materialName = response.materialName,
            materialText = response.materialText,
            materialPriority = response.materialPriority,
            knowledgeAreas = areas,
            subProfessions = subProfessions
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
        val subProfessions = ArrayList<SubProfessionDomain>()
        response.forEach {
            material->
            val knowledgeAreas = ArrayList<KnowledgeAreaDomain>()
            material.knowledgeAreas.forEach { knowledgeArea ->
                knowledgeAreas.add(KnowledgeAreaDomain(
                    areaId = knowledgeArea.areaId,
                    areaName = knowledgeArea.areaName
                ))
            }
            material.subProfessions.forEach { subProf->
                subProfessions.add(SubProfessionDomain(
                    professionId = subProf.professionId,
                    professionName = subProf.professionName
                ))
            }
            materialsData.add((MaterialDomain(
                materialId = material.materialId,
                materialName = material.materialName,
                materialText = material.materialText,
                materialPriority = material.materialPriority,
                knowledgeAreas = knowledgeAreas,
                subProfessions = subProfessions
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
            val areas = ArrayList<KnowledgeArea>()
            val subProfessions = ArrayList<ProfessionListItem>()
            material.knowledgeAreas.forEach { area->
                areas.add(KnowledgeArea(
                    areaId = area.areaId,
                    areaName = area.areaName
                ))
            }
            material.subProfessions.forEach { subProf->
                subProfessions.add(ProfessionListItem(
                    professionId = subProf.professionId,
                    professionName = subProf.professionName
                ))
            }
            materialData.add(Material(
                materialId = material.materialId,
                materialName = material.materialName,
                materialText = material.materialText,
                materialPriority = material.materialPriority,
                knowledgeAreas = areas,
                subProfessions = subProfessions
            ))
        }
        return materialData
    }

    private fun mapToEventChip(sub: KnowledgeAreaDomain): Chip {
        return Chip(
            id = sub.areaId,
            name = sub.areaName
        )
    }

    fun mapToChips(materialsList: List<MaterialDomain>): List<Chip>{
        val list = HashSet<Chip>()
        materialsList.forEach { material->
            material.knowledgeAreas.forEach {area->
                list.add(mapToEventChip(area))
            }
        }
        return list.toList()
    }

    fun mapToMaterialsExpandedItems(listMaterialsDomain: List<MaterialDomain>):List<MaterialSectionItem>{
        val list = hashSetOf<MaterialSectionItem>()
        listMaterialsDomain.forEach { material->
            val tags = ArrayList<String>()
            material.knowledgeAreas.forEach {
                tags.add(it.areaName)
            }
            list.add(MaterialSectionItem(
                materialId = material.materialId,
                headerText = material.materialName,
                materialText = material.materialText,
                materialTags = tags
            ))
        }

        return list.toList()
    }



}