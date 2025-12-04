package com.example.el_gordon.models

import java.io.Serializable

data class Player(
    var avatar: Int? = null,
    var nombreJugador: String = "",
    var numPreguntas: Int? = null,
    var dificultad: String? = null,
    var puntuacion: Int? = null,
    var errores: Int? = null,
    var tiempoPartida: String? = null,
    var fechaHorainicio: String? = null
) : Serializable