package com.swirl.pocketarcade.hangman.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HangmanStatus(
    val currentProgress: String,
    val incorrectGuesses: Int,
    val maxIncorrectGuesses: Int,
    val isGameOver: Boolean,
    val isWon: Boolean
): Parcelable