package com.swirl.pocketarcade.data.remote.api

import com.swirl.pocketarcade.data.remote.model.WordResponse
import retrofit2.http.GET

interface WordApiService {
    @GET("words/random")
    suspend fun getRandomWord(): WordResponse
}