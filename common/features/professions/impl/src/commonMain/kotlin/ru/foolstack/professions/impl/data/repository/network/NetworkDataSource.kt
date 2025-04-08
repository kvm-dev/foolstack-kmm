package ru.foolstack.professions.impl.data.repository.network

import ru.foolstack.professions.api.model.ProfessionDomain
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.professions.impl.mapper.Mapper

class NetworkDataSource(private val api: ProfessionsApi, private val mapper: Mapper) {

    suspend fun getProfessions(): ProfessionsDomain {
        val response = api.getProfessions()
        val professionsList = HashSet<ProfessionDomain>()
        response.professions.forEach { profession ->

            professionsList.add(mapper.recursiveProfessionsMapping(profession))
            if (profession.subProfessions.isNotEmpty()) {
                response.professions.forEach { subProfLvl1 ->
                professionsList.add(
                    mapper.recursiveProfessionsMapping(
                        subProfLvl1
                    )
                )
                if (subProfLvl1.subProfessions.isNotEmpty()) {
                subProfLvl1.subProfessions.forEach { subProfLvl2 ->
                    professionsList.add(
                        mapper.recursiveProfessionsMapping(
                            subProfLvl2
                        )
                    )
                    if (subProfLvl2.subProfessions.isNotEmpty()) {
                    subProfLvl2.subProfessions.forEach { subProfLvl3 ->
                        professionsList.add(
                            mapper.recursiveProfessionsMapping(
                                subProfLvl3
                            )
                        )
                        if (subProfLvl3.subProfessions.isNotEmpty()) {
                        subProfLvl3.subProfessions.forEach { subProfLvl4 ->
                            professionsList.add(
                                mapper.recursiveProfessionsMapping(
                                    subProfLvl4
                                )
                            )
                            if (subProfLvl4.subProfessions.isNotEmpty()) {
                            subProfLvl4.subProfessions.forEach { subProfLvl5 ->
                                professionsList.add(
                                    mapper.recursiveProfessionsMapping(
                                        subProfLvl5
                                    )
                                )
                            }
                        }
                        }
                    }
                    }
                }
                }
            }
            }
        }
        }
        return ProfessionsDomain(
            success = response.success,
            professions = mapper.correctListProfessions(professionsList),
            errorMsg = response.errorMsg
        )
    }
}