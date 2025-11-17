package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_nivel1)
        hideSystemUI()

        val tvScoreTitle = findViewById<TextView>(R.id.tvScoreTitle)
        val btnBack = findViewById<Button>(R.id.btnBackToLevels)

        // Recibimos datos desde la actividad anterior
        val level = intent.getIntExtra("LEVEL", 1)
        val scoreStars = intent.getIntExtra("SCORE_STARS", 0).coerceIn(0, 3) // validamos 0-3

        tvScoreTitle.text = "Nivel $level - Score Final"

        // Configurar las estrellas
        val stars = listOf(
            findViewById<ImageView>(R.id.star1),
            findViewById<ImageView>(R.id.star2),
            findViewById<ImageView>(R.id.star3)
                          )

        for (i in stars.indices) {
            if (i < scoreStars) {
                stars[i].setImageResource(R.drawable.star)
                // Animación al aparecer
                stars[i].animate().scaleX(1.2f).scaleY(1.2f).setDuration(300).withEndAction {
                    stars[i].scaleX = 1f
                    stars[i].scaleY = 1f
                }.start()
            } else {
                stars[i].setImageResource(R.drawable.estrella_vacia)
            }
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, LevelSelector::class.java)
            startActivity(intent)
            finish()
        }
    }
}
