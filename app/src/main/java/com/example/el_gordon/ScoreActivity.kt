package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.el_gordon.data.GameData
import com.example.el_gordon.util.JsonWriter
import com.tuapp.utils.hideSystemUI

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_nivel1)
        hideSystemUI()

        val endMillis = System.currentTimeMillis()
        val elapsedMillis = endMillis - GameData.startTimeMillis
        val elapsedSeconds = elapsedMillis / 1000

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

        val difficultySelected = when (difficulty) {
            1 -> "Fácil"
            2 -> "Medio"
            3 -> "Difícil"
            else -> "error"
        }

        GameData.player?.difficulty = difficultySelected
        GameData.player?.score = earnedStars
        GameData.player?.errors = wrongIngredientsCount
        GameData.player?.matchTime = elapsedSeconds.toString()

        val jsonWriter = JsonWriter(this)
        jsonWriter.saveGameDataToJson()

        btnBack.setOnClickListener {
            startActivity(Intent(this, LevelSelector::class.java))
            finish()
        }
    }
}