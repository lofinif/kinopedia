package com.example.kinopedia.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


sealed class CallResult<out T> {
    data class Success<out T>(val value: T): CallResult<T>()
    data class HttpError(val code: Int? = null, val message: String?): CallResult<Nothing>()
    object IOError: CallResult<Nothing>()
    object UnknownError: CallResult<Nothing>()

}
open class BaseRepo {

}

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO,
                            apiCall: suspend () -> T): CallResult<T> {
return withContext(dispatcher){
    try {
        CallResult.Success(apiCall.invoke())
    } catch (throwable: Throwable){
        when(throwable){
            is IOException -> CallResult.IOError
            is HttpException -> CallResult.HttpError(throwable.code(), throwable.message())
            else -> CallResult.UnknownError
        }
    }
}

}