package com.example.el_gordon.models

import java.io.Serializable

data class Player(
    var avatar: Int? = null,
    var playerName: String = "",
    var numQuestions: Int? = null,
    var difficulty: String? = null,
    var score: Int? = null,
    var errors: Int? = null,
    var matchTime: String? = null,
    var startDateTime: String? = null
) : Serializable