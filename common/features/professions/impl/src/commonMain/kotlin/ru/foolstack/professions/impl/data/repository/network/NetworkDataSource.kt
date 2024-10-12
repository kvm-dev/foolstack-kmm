package ru.foolstack.professions.impl.data.repository.network

import ru.foolstack.network.utils.getBase64Bitmap
import ru.foolstack.professions.api.model.ProfessionDomain
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.professions.impl.mapper.Mapper

class NetworkDataSource(private val api: ProfessionsApi, private val mapper: Mapper) {

    suspend fun getProfessions(): ProfessionsDomain {
        val response = api.getProfessions()
        val professionsList = HashSet<ProfessionDomain>()
        response.professions.forEach { profession ->
            val base64Image = if (profession.professionImageUrl.isNotEmpty()) {
                getBase64Bitmap(profession.professionImageUrl)
            } else ""
            professionsList.add(mapper.recursiveProfessionsMapping(profession, base64Image))
            if (profession.subProfessions.isNotEmpty()) {
                response.professions.forEach { subProfLvl1 ->
                val subProfLvl1base64Image = if (subProfLvl1.professionImageUrl.isNotEmpty()) {
                    getBase64Bitmap(subProfLvl1.professionImageUrl)
                } else ""
                professionsList.add(
                    mapper.recursiveProfessionsMapping(
                        subProfLvl1,
                        subProfLvl1base64Image
                    )
                )
                if (subProfLvl1.subProfessions.isNotEmpty()) {
                subProfLvl1.subProfessions.forEach { subProfLvl2 ->
                    val subProfLvl2base64Image = if (subProfLvl2.professionImageUrl.isNotEmpty()) {
                        getBase64Bitmap(subProfLvl2.professionImageUrl)
                    } else ""
                    professionsList.add(
                        mapper.recursiveProfessionsMapping(
                            subProfLvl2,
                            subProfLvl2base64Image
                        )
                    )
                    if (subProfLvl2.subProfessions.isNotEmpty()) {
                    subProfLvl2.subProfessions.forEach { subProfLvl3 ->
                        val subProfLvl3base64Image =
                            if (subProfLvl3.professionImageUrl.isNotEmpty()) {
                                getBase64Bitmap(subProfLvl3.professionImageUrl)
                            } else ""
                        professionsList.add(
                            mapper.recursiveProfessionsMapping(
                                subProfLvl3,
                                subProfLvl3base64Image
                            )
                        )
                        if (subProfLvl3.subProfessions.isNotEmpty()) {
                        subProfLvl3.subProfessions.forEach { subProfLvl4 ->
                            val subProfLvl4base64Image =
                                if (subProfLvl4.professionImageUrl.isNotEmpty()) {
                                    getBase64Bitmap(subProfLvl4.professionImageUrl)
                                } else ""
                            professionsList.add(
                                mapper.recursiveProfessionsMapping(
                                    subProfLvl4,
                                    subProfLvl4base64Image
                                )
                            )
                            if (subProfLvl4.subProfessions.isNotEmpty()) {
                            subProfLvl4.subProfessions.forEach { subProfLvl5 ->
                                val subProfLvl5base64Image =
                                    if (subProfLvl5.professionImageUrl.isNotEmpty()) {
                                        getBase64Bitmap(subProfLvl5.professionImageUrl)
                                    } else ""
                                professionsList.add(
                                    mapper.recursiveProfessionsMapping(
                                        subProfLvl5,
                                        subProfLvl5base64Image
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