package com.example.el_gordon.util

import android.content.Context
import com.example.el_gordon.data.GameData
import com.example.el_gordon.models.Player
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonWriter(private val context: Context) {

    fun saveGameDataToJson() {
        val gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()
        val player = GameData.player ?: return

        // Crear un objeto PlayerData (igual que Player)
        val playerData = Player(
            avatar = player.avatar,
            playerName = player.playerName,
            numQuestions = player.numQuestions,
            difficulty = player.difficulty,
            score = player.score,
            errors = player.errors,
            matchTime = player.matchTime,
            startDateTime = player.startDateTime
                               )

        val jsonDir = File(context.filesDir, "jsons")
        if (!jsonDir.exists()) jsonDir.mkdirs()
        val file = File(jsonDir, "stats.json")

        // Leer lista existente de Player
        val playerList: MutableList<Player> = if (file.exists()) {
            val type = object : TypeToken<MutableList<Player>>() {}.type
            gson.fromJson(file.readText(), type) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        // Agregar jugador actual
        playerList.add(playerData)

        // Guardar lista completa en JSON
        file.writeText(gson.toJson(playerList))
    }
}
