package ru.foolstack.storage

import ru.foolstack.storage.mapper.Mapper
import ru.foolstack.storage.model.Books
import ru.foolstack.storage.model.Events
import ru.foolstack.storage.model.Materials
import ru.foolstack.storage.model.News
import ru.foolstack.storage.model.PassedTest
import ru.foolstack.storage.model.PassedTests
import ru.foolstack.storage.model.Professions
import ru.foolstack.storage.model.Profile
import ru.foolstack.storage.model.Studies
import ru.foolstack.storage.model.Tests

class DatabaseSdk(databaseDriverFactory: DatabaseDriverFactory, mapper: Mapper) {
    private val database = Database(databaseDriverFactory, mapper)

    suspend fun getProfile(): Profile = database.getProfile()
    suspend fun saveProfile(profile: Profile) = database.clearAndSaveProfile(profile)

    suspend fun getMaterials():Materials = database.getMaterials()

    suspend fun saveMaterials(materials: Materials) = database.clearAndSaveMaterials(materials)

    suspend fun getTests():Tests = database.getTests()

    suspend fun saveTests(tests:Tests) = database.clearAndSaveTests(tests)

    suspend fun getPassedTests():PassedTests = database.getPassedTests()

    suspend fun savePassedTests(passedTests:PassedTests) = database.clearAndSavePassedTests(passedTests)

    suspend fun savePassedTest(passedTest:PassedTest) = database.addPassedTest(passedTest)

    suspend fun getEvents() =  database.getEvents()

    suspend fun saveEvents(events: Events) = database.clearAndSaveEvents(events)

    suspend fun getNews(): News = database.getNews()

    suspend fun saveNews(news: News) =  database.clearAndSaveNews(news)

    suspend fun getBooks() = database.getBooks()

    suspend fun saveBooks(books: Books) = database.clearAndSaveBooks(books)

    suspend fun getStudies() = database.getStudies()

    suspend fun saveStudies(studies: Studies) = database.clearAndSavStudies(studies)

    suspend fun getProfessions() = database.getProfessions()

    suspend fun saveProfessions(professions: Professions) = database.clearAndSaveProfessions(professions)

    suspend fun clearProfileAndPassedTests() = database.clearProfileAndPassedTests()
}