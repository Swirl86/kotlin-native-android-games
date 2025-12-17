package com.swirl.pocketarcade.data.repository

import com.swirl.pocketarcade.data.remote.api.WordApiService
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val api: WordApiService
) {

    suspend fun fetchRandomWord(): String =
        api.getRandomWord().word.lowercase()
}