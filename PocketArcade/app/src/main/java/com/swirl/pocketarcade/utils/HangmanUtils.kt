package com.swirl.pocketarcade.utils

import com.swirl.pocketarcade.games.hangman.model.HangmanPart

object HangmanUtils {

    val SWEDISH_ALPHABET = ('A'..'Z') + listOf('Å', 'Ä', 'Ö')

    private val PARTS_BY_STEP_4 = listOf(
        listOf(HangmanPart.BASE, HangmanPart.POLE),
        listOf(HangmanPart.SUPPORT, HangmanPart.BEAM, HangmanPart.ROPE),
        listOf(HangmanPart.HEAD, HangmanPart.BODY, HangmanPart.LEFT_ARM, HangmanPart.RIGHT_ARM),
        listOf(HangmanPart.LEFT_LEG, HangmanPart.RIGHT_LEG, HangmanPart.FACE)
    )

    private val PARTS_BY_STEP_6 = listOf(
        listOf(HangmanPart.BASE, HangmanPart.POLE),
        listOf(HangmanPart.SUPPORT, HangmanPart.BEAM),
        listOf(HangmanPart.ROPE),
        listOf(HangmanPart.HEAD, HangmanPart.BODY),
        listOf(HangmanPart.LEFT_ARM, HangmanPart.RIGHT_ARM),
        listOf(HangmanPart.LEFT_LEG, HangmanPart.RIGHT_LEG, HangmanPart.FACE)
    )

    private val PARTS_BY_STEP_8 = listOf(
        listOf(HangmanPart.BASE),
        listOf(HangmanPart.POLE),
        listOf(HangmanPart.SUPPORT),
        listOf(HangmanPart.BEAM),
        listOf(HangmanPart.ROPE),
        listOf(HangmanPart.HEAD, HangmanPart.BODY),
        listOf(HangmanPart.LEFT_ARM, HangmanPart.RIGHT_ARM),
        listOf(HangmanPart.LEFT_LEG, HangmanPart.RIGHT_LEG, HangmanPart.FACE)
    )

    private val PARTS_BY_STEP_10 = listOf(
        listOf(HangmanPart.BASE),
        listOf(HangmanPart.POLE),
        listOf(HangmanPart.SUPPORT),
        listOf(HangmanPart.BEAM),
        listOf(HangmanPart.ROPE),
        listOf(HangmanPart.HEAD),
        listOf(HangmanPart.BODY),
        listOf(HangmanPart.LEFT_ARM),
        listOf(HangmanPart.RIGHT_ARM),
        listOf(HangmanPart.LEFT_LEG, HangmanPart.RIGHT_LEG, HangmanPart.FACE)
    )

    private val PARTS_BY_STEP_11 = listOf(
        listOf(HangmanPart.BASE),
        listOf(HangmanPart.POLE),
        listOf(HangmanPart.SUPPORT),
        listOf(HangmanPart.BEAM),
        listOf(HangmanPart.ROPE),
        listOf(HangmanPart.HEAD),
        listOf(HangmanPart.BODY),
        listOf(HangmanPart.LEFT_ARM),
        listOf(HangmanPart.RIGHT_ARM),
        listOf(HangmanPart.LEFT_LEG),
        listOf(HangmanPart.RIGHT_LEG, HangmanPart.FACE)
    )

    /**
     * Returns the list of HangmanParts to display based on
     * number of wrong guesses and maxIncorrectGuesses.
     */
    fun getPartsForIncorrectGuess(
        incorrectGuesses: Int,
        maxIncorrectGuesses: Int
    ): List<HangmanPart> {
        if (incorrectGuesses <= 0) return emptyList()

        val steps = when (maxIncorrectGuesses) {
            4 -> PARTS_BY_STEP_4
            6 -> PARTS_BY_STEP_6
            8 -> PARTS_BY_STEP_8
            10 -> PARTS_BY_STEP_10
            11 -> PARTS_BY_STEP_11
            else -> PARTS_BY_STEP_6 // fallback
        }

        return steps.take(incorrectGuesses.coerceAtMost(steps.size)).flatten()
    }

    private val defaultWords = listOf(
        "KOTLIN",
        "ANDROID",
        "HANGMAN",
        "COMPOSE",
        "FRAGMENT",
        "RECYCLER",
        "VIEWMODEL"
    )

    fun getRandomDefaultWord(): String = defaultWords.random()
}