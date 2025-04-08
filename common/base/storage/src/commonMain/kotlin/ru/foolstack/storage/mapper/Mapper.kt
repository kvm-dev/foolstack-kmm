package ru.foolstack.storage.mapper

import ru.foolstack.storage.model.Book
import ru.foolstack.storage.model.Event
import ru.foolstack.storage.model.EventSub
import ru.foolstack.storage.model.Events
import ru.foolstack.storage.model.KnowledgeArea
import ru.foolstack.storage.model.New
import ru.foolstack.storage.model.News
import ru.foolstack.storage.model.PassedTest
import ru.foolstack.storage.model.PassedTests
import ru.foolstack.storage.model.Professions
import ru.foolstack.storage.model.Profession
import ru.foolstack.storage.model.ProfessionListItem
import ru.foolstack.storage.model.Study
import ru.foolstack.storage.model.UserAchievement
import ru.foolstack.storage.model.Variant
import rufoolstackstorageimplcache.EventSubs
import rufoolstackstorageimplcache.MaterialsKnowledgeAreas
import rufoolstackstorageimplcache.MaterialsSubProfessions
import rufoolstackstorageimplcache.TestProfessions
import rufoolstackstorageimplcache.TestQuestionVariant
import rufoolstackstorageimplcache.UserAchievments
import rufoolstackstorageimplcache.UserPurchasedProfessions
import rufoolstackstorageimplcache.Professions as ProfessionsLocal
import rufoolstackstorageimplcache.Events as EventsLocal
import rufoolstackstorageimplcache.News as NewsLocal
import rufoolstackstorageimplcache.Books as BooksLocal
import rufoolstackstorageimplcache.Studies as StudyLocal
import rufoolstackstorageimplcache.EventSubs as EventSubsLocal
import rufoolstackstorageimplcache.PassedTests as PassedTestsLocal


class Mapper {
    fun mapAchievement(response: List<UserAchievments>):List<UserAchievement>{
        val userAchievements = ArrayList<UserAchievement>()
        response.forEach { achievement->
            userAchievements.add(
                UserAchievement(
                    achievementId = achievement.achievementId.toInt(),
                    userId = achievement.userId.toInt(),
                    achievementName = achievement.achievementName,
                    achievementDescription = achievement.achievementDescription,
                    achievementLevel = achievement.achievementLevel.toInt()
                )
            )
        }
        return userAchievements
    }

    fun mapPurchasedProfessions(response: List<UserPurchasedProfessions>):List<Int>{
        val userPurchasedProfessions = ArrayList<Int>()
        response.forEach {purchasedProfession->
            userPurchasedProfessions.add(purchasedProfession.professionId.toInt())
        }
        return userPurchasedProfessions
    }

    fun mapKnowledgeAreas(response: List<MaterialsKnowledgeAreas>):List<KnowledgeArea>{
        val materialKnowledgeAreas = ArrayList<KnowledgeArea>()
        response.forEach {knowledgeArea->
            materialKnowledgeAreas.add(KnowledgeArea(
                areaId = knowledgeArea.knowledgeAreaId.toInt(),
                areaName = knowledgeArea.knowledgeAreaName
            ))
        }
        return materialKnowledgeAreas
    }

    fun mapMaterialSubProfessions(response: List<MaterialsSubProfessions>):List<ProfessionListItem>{
        val materialSubProfessions = ArrayList<ProfessionListItem>()
        response.forEach {subProfession->
            materialSubProfessions.add(ProfessionListItem(
                professionId = subProfession.materialSubProfessionId.toInt(),
                professionName = subProfession.materialSubProfessionName
                ))
        }
        return materialSubProfessions
    }

    fun mapTestProfessions(response: List<TestProfessions>):List<ProfessionListItem>{
        val testProfessions = ArrayList<ProfessionListItem>()
        response.forEach { profession->
            testProfessions.add(ProfessionListItem(
                professionId = profession.professionId.toInt(),
                professionName = profession.professionName
            ))
        }
        return testProfessions
    }

     fun mapQuestionVariants(response: List<TestQuestionVariant>): List<Variant>{
        val variantsList = ArrayList<Variant>()
        response.forEach { variant->
            variantsList.add(Variant(
                variantId = variant.variantId.toInt(),
                variantText = variant.variantText,
                isRight = variant.isRight.toInt()==1
            ))
        }
        return variantsList
    }

     fun mapPassedTests(passedTests: List<PassedTestsLocal>):PassedTests{
        val list = ArrayList<PassedTest>()
         passedTests.forEach { passedTest->
             list.add(
                 PassedTest(
                 testId = passedTest.testId.toInt(),
                 testResult = passedTest.testResult.toInt(),
                 finishTestTime = passedTest.finishTestTime
             )
             )
         }
        return PassedTests(
            passedTests = list
        )
    }

    private fun mapProfession(profession: ProfessionsLocal):Profession{
        return Profession(
            professionId = profession.professionId.toInt(),
            professionName = profession.professionName,
            professionPriority = profession.professionPriority.toInt(),
            professionParent = profession.professionParent.toInt(),
            professionType = profession.professionType.toInt(),
            professionImageBase64 = profession.professionImageBase64,
            subProfessions = listOf()
        )
    }

    fun mapProfessions(professions: List<ProfessionsLocal>): Professions{
        val profList = ArrayList<Profession>()
        professions.filter { it.professionType == 0L }.forEach {generalProfession->
            profList.add(mapProfession(generalProfession))
        }
        profList.forEach { profession->
            val subProfs = ArrayList<Profession>()
            professions.filter { it.professionParent == profession.professionId.toLong() }.forEach {
                subProfs.add(mapProfession(it))
            }
            profession.subProfessions = subProfs
        }

        profList.forEach {lvl0->
            lvl0.subProfessions.forEach {lvl1->
                val subProfs = ArrayList<Profession>()
                professions.filter { it.professionParent == lvl1.professionId.toLong() }.forEach {
                    subProfs.add(mapProfession(it))
                }
                lvl1.subProfessions = subProfs
            }
        }

        profList.forEach {lvl0->
            lvl0.subProfessions.forEach {lvl1->
                lvl1.subProfessions.forEach { lvl2->
                    val subProfs = ArrayList<Profession>()
                    professions.filter { it.professionParent == lvl2.professionId.toLong() }.forEach {
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
                        val subProfs = ArrayList<Profession>()
                        professions.filter { it.professionParent == lvl3.professionId.toLong() }.forEach {
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
                            val subProfs = ArrayList<Profession>()
                            professions.filter { it.professionParent == lvl4.professionId.toLong() }.forEach {
                                subProfs.add(mapProfession(it))
                            }
                            lvl4.subProfessions = subProfs
                        }
                    }
                }
            }
        }
        return Professions(
            errorMsg = "",
            professions = profList
        )
    }

    fun mapEvents(events: List<EventsLocal>, eventsSubs: List<EventSubs>): Events{
        val eventsList = ArrayList<Event>()
        events.forEach { event->
            eventsList.add(mapEvent(event = event, eventSubs = mapEventSubs(eventsSubs.filter { it.eventId == event.eventId })))
        }
        return Events(errorMsg = "",
            events = eventsList)
    }

    private fun mapEvent(event:EventsLocal, eventSubs: List<EventSubsLocal>): Event{
        val subList = ArrayList<EventSub>()
        eventSubs.forEach {
            subList.add(
                EventSub(
                subId = it.eventSubId.toInt(),
                subName = it.eventSubName
            )
            )
        }
        return Event(
            eventId = event.eventId.toInt(),
            eventName = event.eventName,
            eventDescription = event.eventDescription,
            eventRefLink = event.eventRefLink,
            eventDateStart = event.eventDateStart,
            eventCost = event.eventCost.toInt(),
            eventImageBase64 = event.eventImageBase64,
            eventSubs = subList
        )
    }

    private fun mapEventSubs(eventSubs: List<EventSubs>): List<EventSubsLocal>{
        val list = ArrayList<EventSubsLocal>()
        eventSubs.forEach { eventSub->
            list.add(EventSubsLocal(
                eventId = eventSub.eventId,
                eventSubId = eventSub.eventSubId,
                eventSubName = eventSub.eventSubName
            ))
        }
        return list
    }

    fun mapNews(news: List<NewsLocal>): News{
        val newsList = ArrayList<New>()
        news.forEach { new->
            newsList.add(mapNew(new))
        }
        return News(
            news = newsList,
            errorMsg = ""
        )
    }

    private fun mapNew(new: NewsLocal):New{
        return New(
            newsId = new.newsId.toInt(),
            newsName = new.newsName,
            newsText = new.newsText,
            newsDate = new.newsDate,
            newsLink = new.newsLink,
            newsImageBase64 = new.newsImageBase64
        )
    }


     fun mapBook(book: BooksLocal, professions:List<ProfessionListItem>): Book{
        return Book(
            bookId = book.bookId.toInt(),
            bookName = book.bookName,
            bookDescription = book.bookDescription,
            bookCostWithSale = book.bookCostWithSale.toInt(),
            bookCostWithoutSale = book.bookCostWithoutSale.toInt(),
            bookRefLink = book.bookRefLink,
            bookImageBase64 = book.bookImageBase64,
            professions = professions
        )
    }

    fun mapStudy(study: StudyLocal, professions: List<ProfessionListItem>): Study{
        return Study(
            studyId = study.studyId.toInt(),
            studyName = study.studyName,
            studyCost = study.studyCost.toInt(),
            studyImageBase64 = study.studyImageBase64,
            studyRefLink = study.studyRefLink,
            studySalePercent = study.studySalePercent.toInt(),
            studyLength = study.studyLength.toInt(),
            studyLengthType = study.studyLengthType.toInt(),
            studyOwner = study.studyOwner,
            studyAdditionalText = study.studyAdditionalText,
            professions = professions
        )
    }

}