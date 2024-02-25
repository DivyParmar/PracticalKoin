package com.divy.practicalkoin.repository

import android.util.Log
import com.divy.practicalkoin.network.BaseResponse
import com.divy.practicalkoin.network.Either
import com.divy.practicalkoin.network.MyException
import com.divy.practicalkoin.utility.MyApplication
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {

    suspend fun <R> either(service : suspend () -> R): Either<MyException, R> {

        return try {
            val response = service.invoke()
            if (response is BaseResponse) {
                if (response.status == 401) {
                    //Logger.d("Api Unauthorized >>>>>")
                    if (MyApplication.context != null) {
//                        Prefs.putString(PrefKeys.AuthKey, "")
//                        Prefs.putString(PrefKeys.UserProfile, "")
//                        val i = Intent(MyApplication.context, LoginActivity::class.java)
//                        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                        MyApplication.context!!.startActivity(i)
                    }
                }
            }
            Either.Right(response)
        } catch (e: Exception) {
            Log.e("Response", "catch: Exception "+e.message)
            e.printStackTrace()
            Either.Left(transformException(e))
        }
    }


    private fun transformException(e: Exception): MyException {
        if (e is HttpException) {
            return when (e.code()) {
                422,
                502 -> MyException.JsonException(e)
                500 -> MyException.InternetConnectionException(e)
                404 -> MyException.ServerNotAvailableException(e)
                401 -> MyException.AuthenticationFailedException(e)
                else -> MyException.UnknownException(e)
            }
        } else {
            when (e) {
                is CancellationException -> {
                    return MyException.NetworkCallCancelException(e)
                }

                is ConnectException, is UnknownHostException, is SocketTimeoutException, is SocketException -> {
                    return MyException.InternetConnectionException(e)
                }

                is IllegalStateException, is JsonSyntaxException, is MalformedJsonException -> {
                    return MyException.JsonException(e)
                }
            }
        }
        return MyException.UnknownException(e)
    }
}