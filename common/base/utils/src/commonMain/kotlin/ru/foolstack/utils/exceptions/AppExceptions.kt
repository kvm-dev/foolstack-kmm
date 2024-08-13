package ru.foolstack.utils.exceptions

sealed class AppExceptions : Exception() {

    sealed class NetworkException(
        override val message: String? = null,
        open val errors: Map<String, String>? = null,
    ) : AppExceptions() {

        data object BadRequestException : NetworkException()
        data object InternalServerException : NetworkException()
        data object UnavailableServiceException : NetworkException()

        class UnprocessableEntityException(
            override val message: String? = null,
            override val errors: Map<String, String>?,
        ) : NetworkException(message, errors)

        class OtpAlreadySend(override val message: String) : NetworkException()

        class PaymentUnavailable(override val message: String) : NetworkException()

        sealed class ExchangeException : NetworkException() {
            class NotEnoughResourcesException(
                override val message: String? = null,
            ) : ExchangeException()

            class ExchangeUnavailableException(
                override val message: String? = null,
            ) : ExchangeException()
        }


        sealed class ServiceException : NetworkException() {
            class ServiceSubscribingUnavailable(
                override val message: String
            ) : ServiceException()

            class ServiceUnsubscribingUnavailable(
                override val message: String
            ): ServiceException()

            class ServiceCostNotDefined(
                override val message: String
            ) : ServiceException()

            class InsufficientFunds(
                override val message: String
            ) : ServiceException()

            class ResourceBalanceNotAvailable(
                override val message: String
            ): ServiceException()
        }

        sealed class FinanceExceptions : NetworkException() {
            class SendEmailDetailsFailed(
                override val message: String
            ) : FinanceExceptions()

        }

        sealed class Client : NetworkException() {

            class BalanceNotAvailable(
                override val message: String
            ) : Client()


            sealed class Tariffs : NetworkException() {

                class ChangeFailed(
                    override val message: String
                ) : NetworkException(message)

                class NotAvailableForChange(
                    override val message: String
                ) : NetworkException(message)

                class CostNotDefined(
                    override val message: String
                ) : NetworkException(message)

            }

        }
    }

    sealed class LocalException(
        val data: LocalExceptionData = LocalExceptionData(),
    ) : AppExceptions() {
        object NoInternetConnectionException : LocalException(
            LocalExceptionData()
        )

        object TimeoutException : LocalException(
            LocalExceptionData()
        )

        sealed class Unauthorized : LocalException() {
            class UnauthorizedException(override val message: String? = null) : Unauthorized()

            object ChangeClientException : Unauthorized()
        }

        object InvalidClientException : LocalException(LocalExceptionData())

        object ClientBlockedException : LocalException(LocalExceptionData())

        object InvalidLoginOrPassword : LocalException(LocalExceptionData())

        object ClientNotFound : LocalException(LocalExceptionData())

        class UnknownException(val throwable: Throwable? = null) : LocalException()
    }
}

data class LocalExceptionData(val message: String? = null, val description: String? = null)

fun Throwable.toAppException(): AppExceptions = when (this) {
//    is UnknownHostException, is ConnectException -> AppExceptions.LocalException.NoInternetConnectionException
//    is SocketTimeoutException -> AppExceptions.LocalException.TimeoutException
    is AppExceptions -> this
    else -> AppExceptions.LocalException.UnknownException(this)
}
