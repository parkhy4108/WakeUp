package com.dev_musashi.wakeup.presentation.screen.problem

data class ProblemState(
    val init : Boolean = false,
    val timer : Int = 5,
    val leftNum : Int? = null,
    val rightNum : Int? = null,
    val answer : String = "",
    val check : String = ""
)
