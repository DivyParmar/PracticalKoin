package com.divy.practicalkoin.network

/* Created Kotlin Sealed class for convert kotlin exception to custom exception */
sealed class MyException(t: Throwable) : Exception() {
    class InternetConnectionException(e: Exception) : MyException(e)
    class JsonException(e: Exception) : MyException(e)
    class NetworkCallCancelException(e: Exception) : MyException(e)
    class ServerNotAvailableException(e: Exception) : MyException(e)
    class AuthenticationFailedException(e: Exception) : MyException(e)
    class DatabaseException(e: Exception) : MyException(e)
    class UnknownException(e: Exception) : MyException(e)
}