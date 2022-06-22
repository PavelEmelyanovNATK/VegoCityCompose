package com.emelyanov.vegocity.shared.domain.utils

import com.emelyanov.vegocity.shared.domain.models.RequestErrorType
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> vegoRequestWrapper(request: suspend () -> T) : RequestResult<T> {
    return try {
        RequestResult.Success(data = request())
    } catch (ex: SocketTimeoutException) {
        RequestResult.Error(type = RequestErrorType.ServerNotResponding, exception = ex)
    } catch(ex: SocketException) {
        RequestResult.Error(type = RequestErrorType.ConnectionError, exception = ex)
    } catch(ex: UnknownHostException) {
        RequestResult.Error(type = RequestErrorType.ConnectionError, exception = ex)
    } catch (ex: HttpException) {
        processHttpException(ex)
    } catch (ex: Exception) {
        RequestResult.Error(
            message = ex.message,
            type =RequestErrorType.UnknownError,
            exception = ex
        )
    }
}

private fun <T> processHttpException(ex: HttpException) : RequestResult<T>
= when(ex.code()) {
    400 -> RequestResult.Error(type = RequestErrorType.BadRequest, exception = ex)
    401 -> RequestResult.Error(type = RequestErrorType.Unauthorized, exception = ex)
    404 -> RequestResult.Error(RequestErrorType.NotFound)
    else -> RequestResult.Error(
        message = ex.message,
        type =RequestErrorType.UnknownError,
        exception = ex
    )
}