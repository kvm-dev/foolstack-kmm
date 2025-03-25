package ru.foolstack.study.impl.mapper

import ru.foolstack.storage.model.Studies
import ru.foolstack.storage.model.Study
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.api.model.StudyDomain
import ru.foolstack.study.api.model.StudyProfessionDomain
import ru.foolstack.storage.model.ProfessionListItem
import ru.foolstack.study.impl.model.StudyResponse
import ru.foolstack.ui.model.Chip
import ru.foolstack.ui.model.StudyItem

class Mapper {

    fun map(response: StudyResponse):StudyDomain{
        val professions = ArrayList<StudyProfessionDomain>()
        response.professions.forEach { profession->
            professions.add(StudyProfessionDomain(
                professionId = profession.professionId,
                professionName = profession.professionName
            ))
        }
    return StudyDomain(
                studyId = response.studyId,
                studyName = response.studyName,
                studyCost = response.studyCost,
                studyImageBase64 = response.studyImage,
                studyRefLink = response.studyRefLink,
                studySalePercent = response.studySalePercent,
                studyLength = response.studyLength,
                studyLengthType = response.studyLengthType,
                professions = professions,
                studyOwner = response.studyOwner,
                studyAdditionalText = response.studyAdditionalText
            )
        }

    fun map(response: StudiesDomain): Studies {
        val studiesList = ArrayList<Study>()
        response.studies.forEach { study->
            val professions = ArrayList<ProfessionListItem>()
            study.professions.forEach { profession->
                professions.add(ProfessionListItem(
                    professionId = profession.professionId,
                    professionName = profession.professionName
                ))
            }
            studiesList.add(
                Study(
                studyId = study.studyId,
                studyName = study.studyName,
                studyCost = study.studyCost,
                studyLength = study.studyLength,
                studyLengthType = study.studyLengthType,
                studyAdditionalText = study.studyAdditionalText,
                studyOwner = study.studyOwner,
                studySalePercent = study.studySalePercent,
                studyImageBase64 = study.studyImageBase64,
                studyRefLink = study.studyRefLink,
                professions = professions
            )
            )
        }
        return Studies(
            studies = studiesList,
            prText = response.prText,
            errorMsg = response.errorMsg
        )
    }

    fun map(response: Studies): StudiesDomain {
        val studiesList = ArrayList<StudyDomain>()
        response.studies.forEach { study->
            val professions = ArrayList<StudyProfessionDomain>()
            study.professions.forEach { profession->
                professions.add(StudyProfessionDomain(
                    professionId = profession.professionId,
                    professionName = profession.professionName
                ))
            }
            studiesList.add(
                StudyDomain(
                    studyId = study.studyId,
                    studyName = study.studyName,
                    studyCost = study.studyCost,
                    studyLength = study.studyLength,
                    studyLengthType = study.studyLengthType,
                    studyAdditionalText = study.studyAdditionalText,
                    studyOwner = study.studyOwner,
                    studySalePercent = study.studySalePercent,
                    studyImageBase64 = study.studyImageBase64,
                    studyRefLink = study.studyRefLink,
                    professions = professions
                )
            )
        }
        return StudiesDomain(
            studies = studiesList,
            prText = response.prText,
            errorMsg = response.errorMsg
        )
    }

    fun mapToStudiesItems(studies: List<StudyDomain>):List<StudyItem>{
        val items = HashSet<StudyItem>()

        studies.forEach { item->
            val tags = HashSet<String>()
            item.professions.forEach { tag->
                tags.add(tag.professionName)
            }
            items.add(
                StudyItem(
                studyId = item.studyId,
                studyName = item.studyName,
                studySalePercent = item.studySalePercent,
                studyLength = item.studyLength,
                studyLengthType = item.studyLengthType,
                studyAdditionalText = item.studyAdditionalText,
                studyCost = item.studyCost,
                studyImageBase64 = item.studyImageBase64,
                studyTags = tags.toList(),
                studyRefLink = item.studyRefLink,
                studyOwner = item.studyOwner
            )
            )
        }
        return items.toList()
    }

    private fun mapToStudyChip(sub: StudyProfessionDomain): Chip {
        return Chip(
            id = sub.professionId,
            name = sub.professionName
        )
    }

    fun mapToChips(studiesList: List<StudyDomain>): List<Chip>{
        val list = HashSet<Chip>()
        studiesList.forEach { study->
            study.professions.forEach {sub->
                list.add(mapToStudyChip(sub))
            }
        }
        return list.toList()
    }
}