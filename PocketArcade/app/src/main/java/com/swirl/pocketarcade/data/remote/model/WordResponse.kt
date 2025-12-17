package com.swirl.pocketarcade.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WordResponse(
    val word: String
)