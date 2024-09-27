
sealed class NetworkError(val message: String) {
    class BadRequest : NetworkError("Request Incorrect")
    class Unauthorized : NetworkError("Unauthorized")
    class Forbidden : NetworkError("Access forbidden")
    class NotFound : NetworkError("Method not found")
    class RequestTimeOut : NetworkError("Request execution interval exceeded")
    class InternalServerError : NetworkError("Internal server error")
    class ExpectationFailed : NetworkError("Header request incorrect")
    class TokenExpired : NetworkError("User token is expired")
    class UnknownError : NetworkError("Unknown error")

}