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

        val wrongIngredientsCount = intent.getIntExtra("wrongIngredientsCount", 0)
        val difficulty = intent.getIntExtra("difficulty", 1)

        val rootLayout = findViewById<LinearLayout>(R.id.rootLayout)
        rootLayout.setBackgroundResource(R.drawable.kitchen_entry)

        val btnBack = findViewById<Button>(R.id.btnBackToLevels)

        val stars = listOf(
            findViewById<ImageView>(R.id.star1),
            findViewById<ImageView>(R.id.star2),
            findViewById<ImageView>(R.id.star3)
        )

        val maxStars = difficulty
        val earnedStars = maxStars - wrongIngredientsCount

        for (i in stars.indices) {
            if (i < maxStars) {
                stars[i].visibility = ImageView.VISIBLE
                if (i < earnedStars) {
                    stars[i].setImageResource(R.drawable.star)
                } else {
                    stars[i].setImageResource(R.drawable.star_empty)
                }
            } else {
                stars[i].visibility = ImageView.GONE
            }
        }

        val message = when {
            earnedStars <= 0 -> "¡Oh no! Inténtalo de nuevo"
            earnedStars == maxStars -> "¡Perfecto! Excelente trabajo"
            else -> "¡Bien hecho! Sigue practicando"
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this, LevelSelector::class.java))
            finish()
        }
    }
}