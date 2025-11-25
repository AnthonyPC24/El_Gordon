package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_nivel1)
        hideSystemUI()

        val rootLayout = findViewById<LinearLayout>(R.id.rootLayout)
        rootLayout.setBackgroundResource(R.drawable.kitchen_entry)

        val tvScoreTitle = findViewById<TextView>(R.id.tvScoreTitle)
        val btnBack = findViewById<Button>(R.id.btnBackToLevels)

        // Recibimos nivel y éxito
        val level = intent.getIntExtra("LEVEL", 1)
        val nivelExitoso = intent.getBooleanExtra("LEVEL_SUCCESS", false)

        // Lista de estrellas
        val stars = listOf(
            findViewById<ImageView>(R.id.star1),
            findViewById<ImageView>(R.id.star2),
            findViewById<ImageView>(R.id.star3)
                          )

        // Ocultar y resetear escala
        stars.forEach {
            it.visibility = ImageView.GONE
            it.scaleX = 1f
            it.scaleY = 1f
        }

        // Máximo y mínimo según nivel
        val maxStarsLevel = when (level) {
            1 -> 1
            2 -> 2
            3 -> 3
            else -> 1
        }

        val minStarsLevel = when (level) {
            1 -> 0
            2 -> 1
            3 -> 2
            else -> 0
        }

        // Cantidad de estrellas llenas
        val scoreStarsLevel = if (nivelExitoso) maxStarsLevel else minStarsLevel

        tvScoreTitle.text = "Nivel $level - Score Final"

        // Mostrar estrellas llenas y vacías
        for (i in 0 until maxStarsLevel) {
            stars[i].visibility = ImageView.VISIBLE
            if (i < scoreStarsLevel) {
                stars[i].setImageResource(R.drawable.star)
                stars[i].scaleX = 0f
                stars[i].scaleY = 0f
            } else {
                stars[i].setImageResource(R.drawable.estrella_vacia)
                stars[i].scaleX = 1f
                stars[i].scaleY = 1f
            }
        }

        // Animación POP secuencial para las estrellas llenas
        fun animateStar(index: Int) {
            if (index >= scoreStarsLevel) return
            val star = stars[index]
            star.animate()
                .scaleX(1.3f).scaleY(1.3f).setDuration(250)
                .withEndAction {
                    star.animate()
                        .scaleX(1f).scaleY(1f).setDuration(150)
                        .withEndAction { animateStar(index + 1) }
                        .start()
                }.start()
        }

        animateStar(0)

        // Botón volver a niveles
        btnBack.setOnClickListener {
            startActivity(Intent(this, LevelSelector::class.java))
            finish()
        }
    }
}
