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

        val jsonDir = File(context.filesDir, "jsons")
        if (!jsonDir.exists()) jsonDir.mkdirs()
        val file = File(jsonDir, "stats.json")

        val playerList: MutableList<Player> = if (file.exists()) {
            val type = object : TypeToken<MutableList<Player>>() {}.type
            gson.fromJson(file.readText(), type) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        playerList.add(player)

        val jsonAGuardar = gson.toJson(playerList)

        file.writeText(jsonAGuardar)
    }
}