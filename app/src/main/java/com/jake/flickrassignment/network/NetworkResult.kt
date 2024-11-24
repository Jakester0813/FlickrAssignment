package com.jake.flickrassignment.network

sealed class NetworkResult<T> (val data:T? = null, val error:String? = null) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(e: String, data: T? = null) : NetworkResult<T> (data, e)
}