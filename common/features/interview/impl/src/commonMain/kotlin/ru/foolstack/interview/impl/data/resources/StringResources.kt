package ru.foolstack.interview.impl.data.resources

object StringResources {

    fun getDescriptionText(lang: String?):String{
        return if(lang=="RU"){
            "К сожалению, нет вопросов, которые можно было бы здесь показать.\n\nЗайди в этот раздел, когда будет доступен интернет и мы сохраним вопросы для “оффлайн” режима."
        } else{
            "Unfortunately, there are no questions to show here.\n\nPlease visit this section when the Internet is available and we will save the questions for “offline” mode."
        }
    }

    fun getScreenTitleText(lang: String?):String{
        return if(lang=="RU"){
            "Вопросы на интервью"
        } else{
            "Interview questions"
        }
    }
}
