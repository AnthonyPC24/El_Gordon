package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.el_gordon.data.GameData
import com.example.el_gordon.models.Player
import com.tuapp.utils.hideSystemUI

class PlayerNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_name)
        hideSystemUI()

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val btnComenzar = findViewById<Button>(R.id.btnComenzar)

        btnComenzar.setOnClickListener {
            val nombre = etNombre.text.toString()

            if (nombre.isNotEmpty()) {
                GameData.player = Player(
                    nombreJugador = nombre,
                    avatar = null,
                    numPreguntas = 0,
                    dificultad = null,
                    puntuacion = null,
                    errores = null,
                    tiempoPartida = null,
                    fechaHorainicio = null
                )

                GameData.player?.nombreJugador = nombre

                val intent = Intent(this, AvatarSelector::class.java)
                startActivity(intent)
                @Suppress("DEPRECATION")
                overridePendingTransition(0, 0)
                finish()
            } else {
                Toast.makeText(this, "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
}