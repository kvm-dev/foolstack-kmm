package ru.foolstack.study.impl.mapper

import ru.foolstack.storage.model.Studies
import ru.foolstack.storage.model.Study
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.api.model.StudyDomain
import ru.foolstack.study.api.model.StudyProfessionDomain
import ru.foolstack.storage.model.ProfessionListItem
import ru.foolstack.study.impl.model.StudyResponse

class Mapper {

    fun map(response: StudyResponse, studyImageBase64: String):StudyDomain{
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
                studyImageUrl = response.studyImageUrl,
                studyImageBase64 = studyImageBase64,
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
                studyImageUrl = study.studyImageUrl,
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
                    studyImageUrl = study.studyImageUrl,
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
}