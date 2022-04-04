package com.com.xpensetracker.utils

data class Resource<out T>(val status: Status,val message:String?,val data :T?){

    fun <T>success(data: T?) : Resource<T>{
        return Resource(Status.SUCCESS,null,data)
    }

    fun <T>error(message: String?):Resource<T>{
        return Resource(Status.ERROR,message,null)
    }

    fun <T>loading(data: T?):Resource<T>{
        return Resource(Status.LOADING,null,null)
    }
}