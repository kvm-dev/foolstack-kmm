package ru.foolstack.professions.impl.mapper

import ru.foolstack.network.utils.getBase64Bitmap
import ru.foolstack.professions.api.model.ProfessionDomain
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.professions.impl.model.ProfessionResponse
import ru.foolstack.professions.impl.model.ProfessionsResponse
import ru.foolstack.storage.model.Profession
import ru.foolstack.storage.model.Professions

class Mapper {

    private fun mapProfession(profession: Profession): ProfessionDomain {
        return ProfessionDomain(
            professionId = profession.professionId,
            professionName = profession.professionName,
            professionPriority = profession.professionPriority,
            professionParent = profession.professionParent,
            professionType = profession.professionType,
            professionImageUrl = profession.professionImageUrl,
            professionImageBase64 = profession.professionImageBase64,
            subProfessions = listOf()
        )
    }

    fun mapProfessions(professions: List<Profession>): ProfessionsDomain{
        val uniqueProfessions = HashSet<Profession>()
        professions.forEach { lvl0->
            uniqueProfessions.add(lvl0)
            lvl0.subProfessions.forEach { lvl1->
                uniqueProfessions.add(lvl1)
                lvl1.subProfessions.forEach { lvl2->
                    uniqueProfessions.add(lvl2)
                    lvl2.subProfessions.forEach { lvl3->
                        uniqueProfessions.add(lvl3)
                        lvl3.subProfessions.forEach { lvl4->
                            uniqueProfessions.add(lvl4)
                            lvl4.subProfessions.forEach { lvl5->
                                uniqueProfessions.add(lvl5)
                            }
                        }
                    }
                }
            }
        }

        val profList = ArrayList<ProfessionDomain>()
        uniqueProfessions.filter { it.professionType == 0 }.forEach {generalProfession->
            profList.add(mapProfession(generalProfession))
        }

        profList.forEach { profession->
            val subProfs = ArrayList<ProfessionDomain>()
            uniqueProfessions.filter { it.professionParent == profession.professionId }.forEach {
                subProfs.add(mapProfession(it))
            }
            profession.subProfessions = subProfs
        }

        profList.forEach {lvl0->
            lvl0.subProfessions.forEach {lvl1->
                val subProfs = ArrayList<ProfessionDomain>()
                uniqueProfessions.filter { it.professionParent == lvl1.professionId }.forEach {
                    subProfs.add(mapProfession(it))
                }
                lvl1.subProfessions = subProfs
            }
        }

        profList.forEach {lvl0->
            lvl0.subProfessions.forEach {lvl1->
                lvl1.subProfessions.forEach { lvl2->
                    val subProfs = ArrayList<ProfessionDomain>()
                    uniqueProfessions.filter { it.professionParent == lvl2.professionId }.forEach {
                        subProfs.add(mapProfession(it))
                    }
                    lvl2.subProfessions = subProfs
                }
            }
        }

        profList.forEach {lvl0->
            lvl0.subProfessions.forEach {lvl1->
                lvl1.subProfessions.forEach { lvl2->
                    lvl2.subProfessions.forEach { lvl3->
                        val subProfs = ArrayList<ProfessionDomain>()
                        uniqueProfessions.filter { it.professionParent == lvl3.professionId }.forEach {
                            subProfs.add(mapProfession(it))
                        }
                        lvl3.subProfessions = subProfs
                    }
                }
            }
        }

        profList.forEach {lvl0->
            lvl0.subProfessions.forEach {lvl1->
                lvl1.subProfessions.forEach { lvl2->
                    lvl2.subProfessions.forEach { lvl3->
                        lvl3.subProfessions.forEach { lvl4->
                            val subProfs = ArrayList<ProfessionDomain>()
                            uniqueProfessions.filter { it.professionParent == lvl4.professionId }.forEach {
                                subProfs.add(mapProfession(it))
                            }
                            lvl4.subProfessions = subProfs
                        }
                    }
                }
            }
        }

        return ProfessionsDomain(
            success = true,
            errorMsg = "",
            professions = profList
        )
    }

     fun recursiveProfessionsMapping(profession: ProfessionResponse, base64Images: String): ProfessionDomain{
        return ProfessionDomain(
            professionId = profession.professionId,
            professionName = profession.professionName,
            professionType = profession.professionType,
            professionParent = profession.professionParent,
            professionPriority = profession.professionPriority,
            professionImageUrl = profession.professionImageUrl,
            professionImageBase64 = base64Images
        )
    }

    fun correctListProfessions(profList: HashSet<ProfessionDomain>):List<ProfessionDomain>{
        val correctList = HashSet<ProfessionDomain>()
        profList.filter { it.professionType==0 }.forEach {
            correctList.add(it)
        }
        correctList.forEach { profession->
            profession.subProfessions = profList.filter { it.professionParent == profession.professionId }
        }
        //lvl1
        correctList.forEach { profession->
            profession.subProfessions.forEach { spLvl1->
                spLvl1.subProfessions = profList.filter { it.professionParent == spLvl1.professionId }
            }
        }
        //lvl2
        correctList.forEach { profession->
            profession.subProfessions.forEach { spLvl1->
                spLvl1.subProfessions.forEach { spLvl2->
                    spLvl2.subProfessions = profList.filter {  it.professionParent ==  spLvl2.professionId }
                }
            }
        }
        //lvl3
        correctList.forEach { profession->
            profession.subProfessions.forEach { spLvl1->
                spLvl1.subProfessions.forEach { spLvl2->
                    spLvl2.subProfessions.forEach { spLvl3->
                        spLvl3.subProfessions = profList.filter { it.professionParent == spLvl3.professionId }
                    }
                }
            }
        }
        //lvl4
        correctList.forEach { profession->
            profession.subProfessions.forEach { spLvl1->
                spLvl1.subProfessions.forEach { spLvl2->
                    spLvl2.subProfessions.forEach { spLvl3->
                        spLvl3.subProfessions.forEach { spLvl4->
                            spLvl4.subProfessions = profList.filter { it.professionParent == spLvl4.professionId }
                        }
                    }
                }
            }
        }
        //lvl5
        correctList.forEach { profession->
            profession.subProfessions.forEach { spLvl1->
                spLvl1.subProfessions.forEach { spLvl2->
                    spLvl2.subProfessions.forEach { spLvl3->
                        spLvl3.subProfessions.forEach { spLvl4->
                            spLvl4.subProfessions.forEach { spLvl5->
                                spLvl5.subProfessions = profList.filter { it.professionParent == spLvl5.professionId }
                            }
                        }
                    }
                }
            }
        }
        return correctList.toList()
    }

    fun mapToProfessions(response: ProfessionsDomain):Professions{
        val professions = HashSet<Profession>()
        val sortedProfessions  = ArrayList<Profession>()
        response.professions.forEach { profession ->
            professions.add(mapToProfession(profession))
            if (profession.subProfessions.isNotEmpty()) {
            profession.subProfessions.forEach { lvl1 ->
                professions.add(mapToProfession(lvl1))
                if (lvl1.subProfessions.isNotEmpty()) {
                lvl1.subProfessions.forEach { lvl2 ->
                    professions.add(mapToProfession(lvl2))
                    if (lvl2.subProfessions.isNotEmpty()) {
                    lvl2.subProfessions.forEach { lvl3 ->
                        professions.add(mapToProfession(lvl3))
                        if (lvl3.subProfessions.isNotEmpty()) {
                        lvl3.subProfessions.forEach { lvl4 ->
                            professions.add(mapToProfession(lvl4))
                            if (lvl4.subProfessions.isNotEmpty()) {
                            lvl4.subProfessions.forEach { lvl5 ->
                                professions.add(mapToProfession(lvl5))
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
        //main profs
        professions.filter { it.professionType==0 }.forEach { profession->
            sortedProfessions.add(profession)
        }
        //lvl0
        sortedProfessions.forEach { profession->
            profession.subProfessions = professions.filter { it.professionParent == profession.professionId }
        }
        //lvl1
        sortedProfessions.forEach { profession->
            if(profession.subProfessions.isNotEmpty()){
                profession.subProfessions.forEach {lvl1->
                    lvl1.subProfessions = professions.filter { it.professionParent == lvl1.professionId }

                }
            }
        }
        //lvl2
        sortedProfessions.forEach { profession ->
            if(profession.subProfessions.isNotEmpty()){
                profession.subProfessions.forEach { lvl1->
                    if(lvl1.subProfessions.isNotEmpty()){
                        lvl1.subProfessions.forEach { lvl2->
                            lvl2.subProfessions = professions.filter { it.professionParent == lvl2.professionId }
                        }
                    }
                }
            }
        }

        //lvl3
        sortedProfessions.forEach { profession ->
            if(profession.subProfessions.isNotEmpty()){
                profession.subProfessions.forEach { lvl1->
                    if(lvl1.subProfessions.isNotEmpty()){
                        lvl1.subProfessions.forEach { lvl2->
                            if(lvl2.subProfessions.isNotEmpty()){
                                lvl2.subProfessions.forEach { lvl3->
                                       lvl3.subProfessions = professions.filter { it.professionParent == lvl3.professionId }
                                }
                            }
                        }
                    }
                }
            }
        }

        //lvl4
        sortedProfessions.forEach { profession ->
            if(profession.subProfessions.isNotEmpty()){
                profession.subProfessions.forEach { lvl1->
                    if(lvl1.subProfessions.isNotEmpty()){
                        lvl1.subProfessions.forEach { lvl2->
                            if(lvl2.subProfessions.isNotEmpty()){
                                lvl2.subProfessions.forEach { lvl3->
                                    if(lvl3.subProfessions.isNotEmpty()){
                                        lvl3.subProfessions.forEach { lvl4->
                                            lvl4.subProfessions = professions.filter { it.professionParent == lvl4.professionId }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //lvl5
        sortedProfessions.forEach { profession ->
            if(profession.subProfessions.isNotEmpty()){
                profession.subProfessions.forEach { lvl1->
                    if(lvl1.subProfessions.isNotEmpty()){
                        lvl1.subProfessions.forEach { lvl2->
                            if(lvl2.subProfessions.isNotEmpty()){
                                lvl2.subProfessions.forEach { lvl3->
                                    if(lvl3.subProfessions.isNotEmpty()){
                                        lvl3.subProfessions.forEach { lvl4->
                                            if(lvl4.subProfessions.isNotEmpty()){
                                                lvl4.subProfessions.forEach { lvl5->
                                                    lvl5.subProfessions = professions.filter { it.professionParent == lvl5.professionId }
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

        return Professions(professions = sortedProfessions,
            errorMsg = response.errorMsg
        )
    }

    private fun mapToProfession(profession: ProfessionDomain):Profession{
        return Profession(
            professionId = profession.professionId,
            professionName = profession.professionName,
            professionPriority = profession.professionPriority,
            professionParent = profession.professionParent,
            professionType = profession.professionType,
            professionImageUrl = profession.professionImageUrl,
            professionImageBase64 = profession.professionImageBase64
        )
    }
}