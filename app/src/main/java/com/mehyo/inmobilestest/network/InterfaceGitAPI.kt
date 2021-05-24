package com.mehyo.inmobilestest.network

import com.mehyo.inmobilestest.GitModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//api interface using suspending function for network calls
interface InterfaceGitAPI {
    //suspending function for network calls
    @GET("repositories")
    suspend fun getAPIResult(
        @Query("q") q: String?,
        @Query("sort") sort:String?,
        @Query("order") order:String?,
        @Query("page") page:Int?,
        @Query("per_page") per_page:Int?
    ): Response<GitModel>


}