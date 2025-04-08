package ru.foolstack.splash.impl.data.resources

object StringResources {
    //text values
    val appTitle = "foolStack"
    fun getSplashTitle(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Поможем найти работу"
        } else{
            "We will help you to find a job"
        }
        return text
    }

    fun getSplashTryAgainButton(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Повторить попытку"
        } else{
            "Try again"
        }
        return text
    }

    fun getSplashPreAuthDescription(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Техническое собеседование или скрининг с HR? - Поможем подготовиться.\n\n" +
                    "Не хватает практики? Проверь себя в наших тестовых заданиях и получи обратную связь."
        } else{
            "Technical interview or screening with HR? - Let's help you get ready.\n\n" +
                    "Do not enough practice? Get test yourself in our test tasks and get feedback."
        }
        return text
    }

    fun getJoinButtonText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Войти в АЙТИ"
        } else{
            "Join to IT"
        }
        return text
    }

    fun getAuthorizationOrRegistrationTitle(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Электронная почта"
        } else{
            "Email"
        }
        return text
    }

    fun getAuthorizationOrRegistrationText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Мы отправим на указанный адрес электронной почты письмо с кодом авторизации.\n\n" +
                    "Если у нас нет пользователя с таким адресом электронной почты, мы тебя зарегистрируем."
        } else{
            "We will send message with an authorization code to the specified email.\n\n" +
                    "If we don't have a user with this email, we will register you."
        }
        return text
    }

    fun getAuthorizationRegistrationButtonText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Войти"
        } else{
            "Sign in"
        }
        return text
    }

    fun getBackButtonText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Назад"
        } else{
            "Go back"
        }
        return text
    }

    fun getConfirmTitleText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Подтверждение"
        } else{
            "Confirmation"
        }
        return text
    }

    fun getConfirmText(lang: String?):String{
        val text = if(lang=="RU"){
            "На твой email отправлен код подтверждения.\n\n" +
                    "Проверь папку “спам”  и введи полученный код."
        } else{
                    "A confirmation code has been sent to your email.\n\n" +
                    "Сheck your spam folder and enter the received code."
        }
        return text
    }

    fun getConfirmButtonText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Подтвердить"
        } else{
            "Confirm"
        }
        return text
    }

    fun getResendButtonText(lang: String?):String{
            return if(lang=="RU"){
                "Получить код заново (seconds сек)"
            } else{
                "Send code again (seconds sec)"
            }
    }

    //errors

    fun getErrorDefaultTitle(lang: String?):String{
        return if(lang=="RU"){
            "Похоже на баг..."
        } else{
            "Looks like a bug..."
        }
    }

     fun getErrorDefaultText(lang: String?):String{
        return if(lang=="RU"){
            "Произошла ошибка, попробуйте еще раз.\n\nВ противном случае свяжитесь с кем-то из наших.\n\nПодробности:\n"
        } else{
            "Try again. Otherwise contact us.\n\nDetails:\n"
        }
    }
    fun getErrorEmailIncorrect(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Email некорректный"
        } else{
            "Email is incorrect"
        }
        return text
    }

    fun getErrorEmailEmpty(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Email не может быть пустым"
        } else{
            "Email is empty"
        }
        return text
    }

    fun getErrorEmailEmptyOrIncorrect(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Email некорректный или пустой"
        } else{
            "Email is empty or incorrect"
        }
        return text
    }

    fun getErrorCodeAlreadySent(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Код уже отправлен"
        } else{
            "Code is already sent"
        }
        return text
    }

    fun getErrorCodeEmptyOrIncorrect(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Код пустой или некорректный"
        } else{
            "Code is empty or incorrect"
        }
        return text
    }

    fun getErrorUserNotFound(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Пользователь не найден"
        } else{
            "User is not found"
        }
        return text
    }

    fun getErrorMethodNotFound(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Метод не найден"
        } else{
            "Method is Not Found"
        }
        return text
    }

    fun getErrorUserUnconfirmed(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Пользователь не подтвержден"
        } else{
            "User is unconfirmed"
        }
        return text
    }

    fun getErrorCodeWrongOrExpired(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Код неправильный или срок действия истек"
        } else{
            "Code is wrong or expired"
        }
        return text
    }

    fun getErrorEmailNotFoundOrInvalidCodeOrUserAlreadyConfirmed(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Email не найден, код неверный или пользователь уже подтвержден"
        } else{
            "Email is not found, invalid verification code or user is already confirmed"
        }
        return text
    }

    fun getErrorUserAlreadyExist(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Пользователь уже зарегистрирован"
        } else{
            "User is already exist"
        }
        return text
    }

    fun getErrorNotFoundConnectionTitle(lang: String?):String{
        return if(lang=="RU"){
            "Отсутствует соединение"
        } else{
            "No connection"
        }
    }

    fun getErrorNotFoundConnectionText(lang: String?):String{
        return if(lang=="RU"){
            "К сожалению, отсутствует интернет. Проверьте подключение к сети интернет для продолжения работы.\n\n" +
                    "После регистрации и авторизации вы сможете пользоваться приложением оффлайн."
        } else{
            "Unfortunately, there is no internet. Check your Internet connection to continue .\n\n "+
                    "After registration and authorization, you can use the application offline."
        }
    }

    fun getErrorNetworkBadRequestText(lang: String?):String{
        return if(lang=="RU"){
            "Некорректный запрос"
        } else{
            "Request Incorrect"
        }
    }

    fun getErrorNetworkExpectationFailedText(lang: String?):String{
        return if(lang=="RU"){
            "Некорректный заголовок запроса"
        } else{
            "Header request incorrect"
        }
    }

    fun getErrorNetworkTokenExpiredText(lang: String?):String{
        return if(lang=="RU"){
            "Срок действия токена истек"
        } else{
            "User token is expired"
        }
    }

    fun getErrorNetworkUnauthorizedText(lang: String?):String{
        return if(lang=="RU"){
            "Пользователь не авторизован"
        } else{
            "Unauthorized"
        }
    }

    fun getErrorNetworkForbiddenText(lang: String?):String{
        return if(lang=="RU"){
            "Доступ запрещен"
        } else{
            "Access forbidden"
        }
    }

    fun getErrorNetworkNotFoundText(lang: String?):String{
        return if(lang=="RU"){
            "Метод не найден"
        } else{
            "Method not found"
        }
    }

    fun getErrorNetworkRequestTimeOutText(lang: String?):String{
        return if(lang=="RU"){
            "Превышен интервал выполнения запроса"
        } else{
            "Request execution interval exceeded"
        }
    }

    fun getErrorNetworkInternalServerText(lang: String?):String{
        return if(lang=="RU"){
            "Внутренняя ошибка сервера"
        } else{
            "Internal server error"
        }
    }

    fun getErrorNetworkUnknownText(lang: String?):String{
        return if(lang=="RU"){
            "Неизвестная ошибка"
        } else{
            "Unknown error"
        }
    }
}
