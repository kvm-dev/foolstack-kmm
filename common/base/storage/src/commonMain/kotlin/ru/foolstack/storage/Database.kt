package ru.foolstack.storage

import ru.foolstack.storage.impl.cache.AppDatabase
import ru.foolstack.storage.mapper.Mapper
import ru.foolstack.storage.model.Book
import ru.foolstack.storage.model.Books
import ru.foolstack.storage.model.Events
import ru.foolstack.storage.model.Material
import ru.foolstack.storage.model.Materials
import ru.foolstack.storage.model.News
import ru.foolstack.storage.model.PassedTest
import ru.foolstack.storage.model.PassedTests
import ru.foolstack.storage.model.Profession
import ru.foolstack.storage.model.Professions
import ru.foolstack.storage.model.Profile
import ru.foolstack.storage.model.Question
import ru.foolstack.storage.model.Studies
import ru.foolstack.storage.model.Study
import ru.foolstack.storage.model.Test
import ru.foolstack.storage.model.Tests
import rufoolstackstorageimplcache.BookProfessions
import ru.foolstack.storage.model.ProfessionListItem

internal class Database(databaseDriverFactory: DatabaseDriverFactory, private val mapper: Mapper) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getProfile(): Profile {
        val profile = dbQuery.selectProfile().executeAsOne()
        val achievements = dbQuery.selectAchievments().executeAsList()
        val purchasedProfessions = dbQuery.selectPurchasedProfessions().executeAsList()
        return Profile(
            userId = profile.userId.toInt(),
            userName = profile.userName,
            userType = profile.userType,
            userStatus = profile.userStatus.toInt(),
            userEmail = profile.userEmail,
            userAchievements = mapper.mapAchievement(achievements),
            userPhotoUrl = profile.userPhotoUrl,
            userPhotoBase64 = profile.userPhotoBase64,
            userPurchasedProfessions = mapper.mapPurchasedProfessions(purchasedProfessions)
        )
    }

    internal fun clearAndSaveProfile(profile: Profile){
        dbQuery.clearAllProfiles()
        dbQuery.clearAllAchievments()
        dbQuery.clearAllPurchasedProfessions()
        dbQuery.insertProfile(
            userId = profile.userId.toLong(),
            userName = profile.userName,
            userType = profile.userType,
            userStatus = profile.userStatus.toLong(),
            userEmail = profile.userEmail,
            userPhotoUrl = profile.userPhotoUrl,
            userPhotoBase64 = profile.userPhotoBase64
        )
        profile.userAchievements.forEach { achievement->
            dbQuery.insertAchievment(
                achievementId = achievement.achievementId.toLong(),
                userId = achievement.userId.toLong(),
                achievementName = achievement.achievementName,
                achievementDescription = achievement.achievementDescription,
                achievementLevel = achievement.achievementLevel.toLong()
            )
        }
        profile.userPurchasedProfessions.forEach { professionId->
            dbQuery.insertPurchasedProfession(
                userId = profile.userId.toLong(),
                professionId = professionId.toLong()
            )
        }
    }

    internal fun clearProfileAndPassedTests(){
        dbQuery.clearAllProfiles()
        dbQuery.clearAllAchievments()
        dbQuery.clearAllPurchasedProfessions()
        dbQuery.clearAllPassedTests()
    }

    internal fun getMaterials(): Materials {
        val materials = ArrayList<Material>()
        dbQuery.selectMaterials().executeAsList().forEach {material ->
            val knowledgeAreas =  mapper.mapKnowledgeAreas(
                dbQuery.selectMaterialsKnowledgeAreas(materialId = material.materialId)
                    .executeAsList())
            val subProfessions = mapper.mapMaterialSubProfessions(
                dbQuery.selectMaterialSubProfessions(materialId = material.materialId)
                    .executeAsList())

            materials.add(Material(
                materialId = material.materialId.toInt(),
                materialName = material.materialName,
                materialText = material.materialText,
                materialPriority = material.materialPriority.toInt(),
                knowledgeAreas = knowledgeAreas,
                subProfessions = subProfessions
            ))
        }
        return Materials(
            materials = materials
        )
    }

    internal fun clearAndSaveMaterials(materials: Materials){
        dbQuery.clearAllMaterials()
        dbQuery.clearAllMaterialsKnowledgeAreas()
        dbQuery.clearAllMaterialSubProfessions()
        materials.materials.forEach { material->
            dbQuery.insertMaterial(
                materialId = material.materialId.toLong(),
                materialName = material.materialName,
                materialText = material.materialText,
                materialPriority = material.materialPriority.toLong()
            )
            material.knowledgeAreas.forEach {knowledgeArea->
                dbQuery.insertMaterialsKnowledgeArea(
                    materialId = material.materialId.toLong(),
                    knowledgeAreaId = knowledgeArea.areaId.toLong(),
                    knowledgeAreaName = knowledgeArea.areaName
                )
            }
            material.subProfessions.forEach { subProfession->
                dbQuery.insertMaterialSubProfession(
                    materialId = material.materialId.toLong(),
                    materialSubProfessionId = subProfession.professionId.toLong(),
                    materialSubProfessionName = subProfession.professionName
                )
            }
        }
    }

    internal fun getTests(): Tests {
        val tests = ArrayList<Test>()
        dbQuery.selectTests().executeAsList().forEach {test ->
            val professions =  mapper.mapTestProfessions(
                dbQuery.selectTestProfessions(testId = test.testId)
                    .executeAsList())
            val questions = ArrayList<Question>()
                dbQuery.selectTestQuestions(testId = test.testId).executeAsList().forEach { question->
                val variants = mapper.mapQuestionVariants(dbQuery.selectTestQuestionVariant(
                    questionId = question.questionId)
                    .executeAsList())
                questions.add(
                    Question(
                    questionId = question.questionId.toInt(),
                    questionText = question.questionText,
                    variants = variants
                )
                )
                }

            tests.add(Test(
                testId = test.testId.toInt(),
                testName = test.testName,
                testLevel = test.testLevel.toInt(),
                testTimeLimit = test.testTimeLimit.toInt(),
                questions = questions,
                professions = professions
            ))
        }
        return Tests(
            tests = tests
        )
    }

    internal fun clearAndSaveTests(tests: Tests){
        dbQuery.clearAllTests()
        dbQuery.clearAllTestProfessions()
        dbQuery.clearAllTestQuestions()
        dbQuery.clearAllTestQuestionVariant()
        tests.tests.forEach { test->
            dbQuery.insertTests(
                testId = test.testId.toLong(),
                testName = test.testName,
                testLevel = test.testLevel.toLong(),
                testTimeLimit = test.testTimeLimit.toLong()
            )
            test.professions.forEach { profession->
                dbQuery.insertTestProfessions(
                    professionId = profession.professionId.toLong(),
                    professionName = profession.professionName,
                    testId = test.testId.toLong()
                )
            }
            test.questions.forEach { question->
                dbQuery.insertTestQuestion(
                    testId = test.testId.toLong(),
                    questionId = question.questionId.toLong(),
                    questionText = question.questionText
                )
                question.variants.forEach { variant->
                    val isRight = if(variant.isRight){
                        1
                    } else{
                        0
                    }
                    dbQuery.insertTestQuestionVariant(
                        questionId = question.questionId.toLong(),
                        variantId = variant.variantId.toLong(),
                        variantText = variant.variantText,
                        isRight = isRight.toLong()
                    )
                }
            }
        }
    }

    internal fun getPassedTests(): PassedTests {
        return mapper.mapPassedTests(dbQuery.selectPassedTests().executeAsList())
    }

    internal fun clearAndSavePassedTests(passedTests: PassedTests){
        dbQuery.clearAllPassedTests()
        passedTests.passedTests.forEach { passedTest->
            dbQuery.insertPassedTest(
                testId = passedTest.testId.toLong(),
                testResult = passedTest.testResult.toLong(),
                finishTestTime = passedTest.finishTestTime
            )
        }
    }

    internal fun addPassedTest(passedTest: PassedTest){
            dbQuery.insertPassedTest(
                testId = passedTest.testId.toLong(),
                testResult = passedTest.testResult.toLong(),
                finishTestTime = passedTest.finishTestTime
            )
    }


    internal fun getProfessions(): Professions {
        return mapper.mapProfessions(dbQuery.selectProfessions().executeAsList())
    }

    internal fun clearAndSaveProfessions(professions: Professions){
        dbQuery.clearAllProfessions()
        for(i in professions.professions.indices){
            dbQuery.insertProfession(professionId = professions.professions[i].professionId.toLong(),
                professionType = professions.professions[i].professionType.toLong(),
                professionName = professions.professions[i].professionName,
                professionParent = professions.professions[i].professionParent.toLong(),
                professionPriority = professions.professions[i].professionPriority.toLong(),
                professionImageUrl = professions.professions[i].professionImageUrl,
                professionImageBase64 = professions.professions[i].professionImageBase64)
            for(a in professions.professions[i].subProfessions.indices){
                dbQuery.insertProfession(professionId = professions.professions[i].subProfessions[a].professionId.toLong(),
                    professionType = professions.professions[i].subProfessions[a].professionType.toLong(),
                    professionName = professions.professions[i].subProfessions[a].professionName,
                    professionParent = professions.professions[i].subProfessions[a].professionParent.toLong(),
                    professionPriority = professions.professions[i].subProfessions[a].professionPriority.toLong(),
                    professionImageUrl = professions.professions[i].professionImageUrl,
                    professionImageBase64 = professions.professions[i].subProfessions[a].professionImageBase64)
                for(b in professions.professions[i].subProfessions[a].subProfessions.indices){
                    dbQuery.insertProfession(professionId = professions.professions[i].subProfessions[a].subProfessions[b].professionId.toLong(),
                        professionType = professions.professions[i].subProfessions[a].subProfessions[b].professionType.toLong(),
                        professionName = professions.professions[i].subProfessions[a].subProfessions[b].professionName,
                        professionParent = professions.professions[i].subProfessions[a].subProfessions[b].professionParent.toLong(),
                        professionPriority = professions.professions[i].subProfessions[a].subProfessions[b].professionPriority.toLong(),
                        professionImageUrl = professions.professions[i].subProfessions[a].subProfessions[b].professionImageUrl,
                        professionImageBase64 = professions.professions[i].subProfessions[a].subProfessions[b].professionImageBase64)
                    for(c in professions.professions[i].subProfessions[a].subProfessions[b].subProfessions.indices){
                        dbQuery.insertProfession(professionId = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].professionId.toLong(),
                            professionType = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].professionType.toLong(),
                            professionName = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].professionName,
                            professionParent = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].professionParent.toLong(),
                            professionPriority = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].professionPriority.toLong(),
                            professionImageUrl = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].professionImageUrl,
                            professionImageBase64 = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].professionImageBase64)
                        for(d in professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions.indices){
                            dbQuery.insertProfession(professionId = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].professionId.toLong(),
                                professionType = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].professionType.toLong(),
                                professionName = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].professionName,
                                professionParent = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].professionParent.toLong(),
                                professionPriority = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].professionPriority.toLong(),
                                professionImageUrl = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].professionImageUrl,
                                professionImageBase64 = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].professionImageBase64)
                            for(e in professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].subProfessions.indices){
                                dbQuery.insertProfession(professionId = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].subProfessions[e].professionId.toLong(),
                                    professionType = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].subProfessions[e].professionType.toLong(),
                                    professionName = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].subProfessions[e].professionName,
                                    professionParent = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].subProfessions[e].professionParent.toLong(),
                                    professionPriority = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].subProfessions[e].professionPriority.toLong(),
                                    professionImageUrl = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].subProfessions[e].professionImageUrl,
                                    professionImageBase64 = professions.professions[i].subProfessions[a].subProfessions[b].subProfessions[c].subProfessions[d].subProfessions[e].professionImageBase64)
                            }
                        }
                    }
                }
            }
        }
    }

    internal fun getEvents(): Events {
        val events = dbQuery.selectEvents().executeAsList()
        val eventsSubs = dbQuery.selectAllEventSubs().executeAsList()

        return mapper.mapEvents(events = events, eventsSubs = eventsSubs)
    }

    internal fun clearAndSaveEvents(events: Events){
        dbQuery.clearAllEvents()
        dbQuery.clearAllEventSubs()
        events.events.forEach {event->
            dbQuery.insertEvents(
                eventId = event.eventId.toLong(),
                eventName = event.eventName,
                eventDescription = event.eventDescription,
                eventCost = event.eventCost.toLong(),
                eventDateStart = event.eventDateStart,
                eventRefLink = event.eventRefLink,
                eventImageUrl = event.eventImageUrl,
                eventImageBase64 = event.eventImageBase64
            )
            event.eventSubs.forEach {eventSub->
                dbQuery.insertEventSubs(eventId = event.eventId.toLong(),
                    eventSubId = eventSub.subId.toLong(),
                    eventSubName = eventSub.subName)
            }
        }
    }

    internal fun getNews(): News {
        return mapper.mapNews(dbQuery.selectNews().executeAsList())
    }

    internal fun clearAndSaveNews(news: News){
        dbQuery.clearAllNews()
        news.news.forEach {new->
            dbQuery.insertNews(
                newsId = new.newsId.toLong(),
                newsName = new.newsName,
                newsText = new.newsText,
                newsDate = new.newsDate,
                newsImageUrl = new.newsImageUrl,
                newsImageBase64 = new.newsImageBase64,
                newsLink = new.newsLink
            )
        }
    }

    internal fun getBooks(): Books {
        val booksPr = dbQuery.selectBooksPr().executeAsList().first()
        val booksLocal = dbQuery.selectBooks().executeAsList()
        val booksList = ArrayList<Book>()
        booksLocal.forEach { book->
            val professionsList = ArrayList<ProfessionListItem>()
            dbQuery.selectBookProfessions().executeAsList().filter { it.bookId == book.bookId }.forEach {
                professionsList.add(ProfessionListItem(
                    professionId = it.professionId.toInt(),
                    professionName = it.professionName
                ))
            }
           booksList.add(mapper.mapBook(
               book = book, professions = professionsList))
        }
        return Books(
            books = booksList,
            maxSalePercent = booksPr.maxSalePercent.toInt(),
            prText = booksPr.prText,
            subscribeMinCost = booksPr.subscribeMinCost.toInt(),
            subscribeText = booksPr.subscribeText,
            subscribeLink = booksPr.subscribeLink,
            errorMsg = ""
        )
    }

    internal fun clearAndSaveBooks(books: Books){
        dbQuery.clearAllBooks()
        dbQuery.clearAllBookPr()
        dbQuery.clearAllBookProfessions()
        dbQuery.insertBooksPr(
            bookPrId = 0L,
            maxSalePercent = books.maxSalePercent.toLong(),
            prText = books.prText,
            subscribeText = books.subscribeText,
            subscribeMinCost = books.subscribeMinCost.toLong(),
            subscribeLink = books.subscribeLink
        )
        books.books.forEach { book->
            val booksProfessions = book.professions
            dbQuery.insertBooks(
                bookId = book.bookId.toLong(),
                bookName = book.bookName,
                bookDescription = book.bookDescription,
                bookImageUrl = book.bookImageUrl,
                bookImageBase64 = book.bookImageBase64,
                bookRefLink = book.bookRefLink,
                bookCostWithoutSale = book.bookCostWithoutSale.toLong(),
                bookCostWithSale = book.bookCostWithSale.toLong()
            )
            booksProfessions.forEach { profession->
                dbQuery.insertBookProfessions(
                    bookId = book.bookId.toLong(),
                    professionId = profession.professionId.toLong(),
                    professionName = profession.professionName
                )
            }
        }
    }

    internal fun getStudies(): Studies {
        val studiesPr = dbQuery.selectStudiesPr().executeAsList().first()
        val studiesLocal = dbQuery.selectStudies().executeAsList()
        val studiesList = ArrayList<Study>()
        studiesLocal.forEach { study->
            val professionsList = ArrayList<ProfessionListItem>()
            dbQuery.selectStudyProfessions().executeAsList().filter { it.studyId == study.studyId }.forEach {
                professionsList.add(ProfessionListItem(
                    professionId = it.professionId.toInt(),
                    professionName = it.professionName
                ))
            }
            studiesList.add(mapper.mapStudy(
                study = study, professions = professionsList))
        }
        return Studies(
            studies = studiesList,
            prText = studiesPr.prText,
            errorMsg = ""
        )
    }

    internal fun clearAndSavStudies(studies: Studies){
        dbQuery.clearAllStudies()
        dbQuery.clearAllStudiesPR()
        dbQuery.clearAllStudyProfessions()
        dbQuery.insertStudiesPr(
            studiesPrId = 0L,
            prText = studies.prText
        )
        studies.studies.forEach { study->
            val studiesProfessions = study.professions
            dbQuery.insertStudy(
                studyId = study.studyId.toLong(),
                studyName = study.studyName,
                studyCost = study.studyCost.toLong(),
                studyImageUrl = study.studyImageUrl,
                studyImageBase64 = study.studyImageBase64,
                studyRefLink = study.studyRefLink,
                studySalePercent = study.studySalePercent.toLong(),
                studyLength = study.studyLength.toLong(),
                studyLengthType = study.studyLengthType.toLong(),
                studyOwner = study.studyOwner,
                studyAdditionalText = study.studyAdditionalText
            )
            studiesProfessions.forEach { profession->
                dbQuery.insertStudyProfessions(
                    professionId = profession.professionId.toLong(),
                    professionName = profession.professionName,
                    studyId = study.studyId.toLong()
                )
            }
        }
    }
}