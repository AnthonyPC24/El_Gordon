package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class LevelSelector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selector)
        hideSystemUI()

        val levels = listOf(
            findViewById<Button>(R.id.btnNivel1) to 1,
            findViewById<Button>(R.id.btnNivel2) to 2,
            findViewById<Button>(R.id.btnNivel3) to 3
        )

        levels.forEach { (button, level) ->
            button.setOnClickListener {
                openRecipeSelector(level)
            }
        }

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun openRecipeSelector(level: Int) {
        val intent = Intent(this, RecipeSelector::class.java)
        intent.putExtra("level", level)
        startActivity(intent)
        @Suppress("DEPRECATION")
        overridePendingTransition(0, 0)
        finish()
    }

}
