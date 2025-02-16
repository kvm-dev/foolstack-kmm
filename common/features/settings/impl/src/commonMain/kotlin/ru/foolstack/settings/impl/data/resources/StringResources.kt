package ru.foolstack.settings.impl.data.resources

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

    fun getGuestButtonText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Войти как гость"
        } else{
            "Login as guest"
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

    fun getErrorUserNameEmpty(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Слишком короткое имя"
        } else{
            "The name is to short"
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

    fun getConfirmChangeEmailDialogTitle(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Обрати внимание"
        } else{
            "Attention"
        }
        return text
    }

    fun getConfirmChangeEmailDialogText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Смена адреса электронной почты не требует подтверждения. В случае если email введен некорректно, ты потеряешь доступ к учетной записи!"
        } else{
            "Changing your email address does'nt  require confirm. If your email is entered incorrectly, you will lose access to your account!"
        }
        return text
    }

    fun getConfirmChangeEmailDialogMainButton(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Да, я хочу изменить email"
        } else{
            "Yes, i wish to change it"
        }
        return text
    }

    fun getConfirmChangeEmailDialogSecondButton(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Не нужно менять email"
        } else{
            "Cancel"
        }
        return text
    }

    fun getConfirmDeleteAccountDialogTitle(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Обрати внимание"
        } else{
            "Attention"
        }
        return text
    }

    fun getConfirmDeleteAccountDialogText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Удаление учетной записи - необратимый процесс. После удаления учетной записи восстановить к ней доступ будет невозможно, все ваши достижения и сведения о вас будут удалены!"
        } else{
            "Deleting an account is an irreversible process. Once you delete an account, it will be impossible to restore access to it, all your achievements and information about you will be deleted!"
        }
        return text
    }

    fun getConfirmDeleteAccountDialogMainButton(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Да, я хочу удалить"
        } else{
            "Yes, i wish to delete it"
        }
        return text
    }

    fun getConfirmDeleteAccountDialogSecondButton(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Отмена"
        } else{
            "Cancel"
        }
        return text
    }





    fun getSuccessDialogTitle(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Успех"
        } else{
            "Success"
        }
        return text
    }

    fun getSuccessDialogText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Данные пользователя успешно изменены"
        } else{
            "User data successfully changed"
        }
        return text
    }

    fun getSuccessDialogBtn(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Ок"
        } else{
            "Ok"
        }
        return text
    }





    fun getFailDialogTitle(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Печаль"
        } else{
            "Sadness"
        }
        return text
    }

    fun getFailDialogText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "При изменении данных пользователя произошла ошибка. Ты можешь попробовать повторить действие позже или связаться с кем-то из наших в крайнем случае"
        } else{
            "There was an error changing user data. You can try again later or contact one of our staff"
        }
        return text
    }

    fun getFailDialogBtn(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Ок"
        } else{
            "Ok"
        }
        return text
    }



    fun getNoConnectionDialogTitle(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Network trouble"
        } else{
            "Network trouble"
        }
        return text
    }

    fun getNoConnectionDialogText(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Похоже, что интернет недоступен или работает нестабильно. Проверьте соединение и повторите попытку"
        } else{
            "The internet appears to be unavailable or slow. Check your network connection and try again"
        }
        return text
    }

    fun getNoConnectionDialogBtn(lang: String?):String{
        val text: String = if(lang=="RU"){
            "Ок"
        } else{
            "Ok"
        }
        return text
    }

}