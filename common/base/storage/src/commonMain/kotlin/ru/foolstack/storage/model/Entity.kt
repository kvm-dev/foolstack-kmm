package ru.foolstack.storage.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.io.encoding.Base64

@Serializable
data class Profile(
    @SerialName("userId") val userId: Int,
    @SerialName("userName") val userName: String,
    @SerialName("userType") val userType: String,
    @SerialName("userStatus") val userStatus: Int,
    @SerialName("userEmail") val userEmail: String,
    @SerialName("userPhotoUrl") val userPhotoUrl: String,
    @SerialName("userPhotoBase64") val userPhotoBase64: String,
    @SerialName("userAchievements") val userAchievements: List<UserAchievement>,
    @SerialName("userPurchasedProfessions") val userPurchasedProfessions: List<Int>
)
@Serializable
data class UserAchievement(
    @SerialName("achievementId") val achievementId: Int,
    @SerialName("userId") val userId: Int,
    @SerialName("achievementName") val achievementName: String,
    @SerialName("achievementDescription") val achievementDescription: String,
    @SerialName("achievementLevel") val achievementLevel: Int
)

@Serializable
data class Materials(
    @SerialName("materials") val materials: List<Material>,
)

@Serializable
data class Material(
    @SerialName("materialId") val materialId: Int,
    @SerialName("materialName") val materialName: String,
    @SerialName("materialText") val materialText: String,
    @SerialName("materialPriority") val materialPriority: Int,
    @SerialName("knowledgeAreas") val knowledgeAreas: List<KnowledgeArea>,
    @SerialName("subProfessions") val subProfessions: List<ProfessionListItem>
)

@Serializable
data class KnowledgeArea(
    @SerialName("areaId") val areaId: Int,
    @SerialName("areaName") val areaName: String
)

@Serializable
data class ProfessionListItem(
    @SerialName("professionId") val professionId: Int,
    @SerialName("professionName") val professionName: String
)

@Serializable
data class Tests(
    @SerialName("tests") val tests: List<Test>
)

@Serializable
data class Test(
    @SerialName("testId") val testId: Int,
    @SerialName("testName") val testName: String,
    @SerialName("testLevel") val testLevel: Int,
    @SerialName("testTimeLimit") val testTimeLimit: Int,
    @SerialName("questions") val questions: List<Question>,
    @SerialName("professions") val professions: List<ProfessionListItem>
)

@Serializable
data class Question(
    @SerialName("questionId") val questionId: Int,
    @SerialName("questionText") val questionText: String,
    @SerialName("variants") val variants: List<Variant>
)

@Serializable
data class Variant(
    @SerialName("variantId") val variantId: Int,
    @SerialName("variantText") val variantText: String,
    @SerialName("isRight") val isRight: Boolean
)

@Serializable
data class Events(
    @SerialName("events") val events: List<Event>,
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class Event(
    @SerialName("eventId") val eventId: Int,
    @SerialName("eventName") val eventName: String,
    @SerialName("eventDescription") val eventDescription: String,
    @SerialName("eventRefLink") val eventRefLink: String,
    @SerialName("eventDateStart") val eventDateStart: Long,
    @SerialName("eventCost") val eventCost: Int,
    @SerialName("eventImageUrl") val eventImageUrl: String,
    @SerialName("eventImageBase64") val eventImageBase64: String,
    @SerialName("eventSubs") val eventSubs: List<EventSub>
)

@Serializable
data class EventSub(
    @SerialName("subId") val subId: Int,
    @SerialName("subName") val subName: String
)

@Serializable
data class News(
    @SerialName("news") val news: List<New>,
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class New(
    @SerialName("newsId") val newsId: Int,
    @SerialName("newsName") val newsName: String,
    @SerialName("newsText") val newsText: String,
    @SerialName("newsDate") val newsDate: Long,
    @SerialName("newsImageUrl") val newsImageUrl: String,
    @SerialName("newsImageBase64") val newsImageBase64: String,
    @SerialName("newsLink") val newsLink: String
)

@Serializable
data class Books(
    @SerialName("books") val books: List<Book>,
    @SerialName("maxSalePercent") val maxSalePercent: Int,
    @SerialName("prText") val prText: String,
    @SerialName("subscribeText") val subscribeText: String,
    @SerialName("subscribeMinCost") val subscribeMinCost: Int,
    @SerialName("subscribeLink") val subscribeLink: String,
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class Book(
    @SerialName("bookId") val bookId: Int,
    @SerialName("bookName") val bookName: String,
    @SerialName("bookDescription") val bookDescription: String,
    @SerialName("bookImageUrl") val bookImageUrl: String,
    @SerialName("bookImageBase64") val bookImageBase64: String,
    @SerialName("bookRefLink") val bookRefLink: String,
    @SerialName("bookCostWithSale") val bookCostWithSale: Int,
    @SerialName("bookCostWithoutSale") val bookCostWithoutSale: Int,
    @SerialName("professions") val professions: List<ProfessionListItem>
)

@Serializable
data class Professions(
    @SerialName("professions") var professions: List<Profession>,
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class Profession(
    @SerialName("professionId") val professionId: Int,
    @SerialName("professionName") val professionName: String,
    @SerialName("professionImageUrl") val professionImageUrl: String,
    @SerialName("professionImageBase64") val professionImageBase64: String,
    @SerialName("professionType") val professionType: Int,
    @SerialName("professionParent") val professionParent: Int,
    @SerialName("professionPriority") val professionPriority: Int,
    @SerialName("subProfessions") var subProfessions: List<Profession> = listOf()
)

@Serializable
data class Studies(
    @SerialName("studies") val studies: List<Study>,
    @SerialName("prText") val prText: String,
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class Study(
    @SerialName("studyId") val studyId: Int,
    @SerialName("studyName") val studyName: String,
    @SerialName("studyCost") val studyCost: Int,
    @SerialName("studyImageUrl") val studyImageUrl: String,
    @SerialName("studyImageBase64") val studyImageBase64: String,
    @SerialName("studyRefLink") val studyRefLink: String,
    @SerialName("studySalePercent") val studySalePercent: Int,
    @SerialName("studyLength") val studyLength: Int,
    @SerialName("studyLengthType") val studyLengthType: Int,
    @SerialName("professions") val professions: List<ProfessionListItem>,
    @SerialName("studyOwner") val studyOwner: String,
    @SerialName("studyAdditionalText")  val studyAdditionalText: String
)
