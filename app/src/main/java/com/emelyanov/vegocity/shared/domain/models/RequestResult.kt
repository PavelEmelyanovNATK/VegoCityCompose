package com.emelyanov.vegocity.shared.domain.models

sealed class RequestResult<T> {
    data class Error<T>(
        val type: RequestErrorType,
        val message: String? = null,
        val exception: Exception? = null
    ) : RequestResult<T>()
    data class Success<T>(val data: T) : RequestResult<T>()

    fun <R> map(mapper: (T) -> R): RequestResult<R>
            = when (this) {
        is Error -> Error(this.type, this.message, this.exception)
        is Success -> Success(mapper(this.data))
    }

    fun getSuccessResult(): Success<T> {
        if(this is Error) throw IllegalStateException()
        return this as Success<T>
    }
}

enum class RequestErrorType {
    ServerNotResponding,
    ConnectionError,
    NotFound,
    BadRequest,
    Unauthorized,
    ParseFailed,
    UnknownError
}