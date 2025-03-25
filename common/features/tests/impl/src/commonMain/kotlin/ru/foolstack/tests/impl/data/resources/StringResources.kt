package ru.foolstack.tests.impl.data.resources

object StringResources {

    fun getDescriptionText(lang: String?):String{
        return if(lang=="RU"){
            "К сожалению, нет тестов, которые можно было бы здесь показать.\n\nЗайди в этот раздел, когда будет доступен интернет и мы сохраним тесты для “оффлайн” режима."
        } else{
            "Unfortunately, there are no tests to show here.\n\nPlease visit this section when the Internet is available and we will save the tests for “offline” mode."
        }
    }

    fun getScreenTitleText(lang: String?):String{
        return if(lang=="RU"){
            "Тесты"
        } else{
            "Tests"
        }
    }
}
