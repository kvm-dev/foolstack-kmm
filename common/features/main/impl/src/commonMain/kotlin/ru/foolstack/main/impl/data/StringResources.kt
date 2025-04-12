package ru.foolstack.main.impl.data

import ru.foolstack.language.api.model.LangResultDomain

object StringResources {

    fun getQuitDialogTitle(lang: String?):String{
        return if(lang=="RU"){
            "Выйти из приложения?"
        } else{
            "Exit from application?"
        }
    }

    fun getQuitDialogDescription(lang: String?):String{
        return if(lang=="RU"){
            "Тебе действительно этого хочется?"
        } else{
            "Do you really want this?"
        }
    }
    fun getQuitDialogMainBtn(lang: String?):String{
        return if(lang=="RU"){
            "Да, я хочу выйти"
        } else{
            "Yes, I want to exit"
        }
    }

    fun getQuitDialogSecondBtn(lang: String?):String{
        return if(lang=="RU"){
            "Пока не буду выходить"
        } else{
            "I'll stay for now"
        }
    }
}