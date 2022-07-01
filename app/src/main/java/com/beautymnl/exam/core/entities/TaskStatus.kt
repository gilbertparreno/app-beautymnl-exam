package com.beautymnl.exam.core.entities

sealed class TaskStatus<out T> {

    data class Loading<out T>(val loading: Boolean) : TaskStatus<T>()
    data class Failure<out T>(val errorMessage: String = "") : TaskStatus<T>() {
        fun isUnknown() = errorMessage == "unknown"
    }
    data class FailureWithException<out T>(val error: Throwable) : TaskStatus<T>()
    data class Success<out T>(val message: String = "") : TaskStatus<T>()
    data class SuccessWithResult<out T>(val result: T) : TaskStatus<T>()

    companion object {
        fun <T> success(message: String = "") = Success<T>(message)
        fun <T> success(result: T) = SuccessWithResult(result = result)
        fun <T> failure(errorMessage: String = "unknown") = Failure<T>(errorMessage)
        fun <T> failure(error: Throwable) = FailureWithException<T>(error)
        fun <T> loading(isLoading: Boolean) = Loading<T>(isLoading)
    }
}