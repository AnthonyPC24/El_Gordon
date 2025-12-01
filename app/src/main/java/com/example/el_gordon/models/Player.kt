package com.example.el_gordon.models

data class Player(
    var avatar: Int,
    var playerName: String,
    var difficulty: String,
    var score: Int,
    var errors: Int,
    var matchTime: String,
    var startDateTime: String
)