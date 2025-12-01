package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class LevelSelector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selector)
        hideSystemUI()

        // Obtener el avatar seleccionado desde el Intent
        val selectedAvatar = intent.getIntExtra("avatar_selected", -1)
        val avatarImageView = findViewById<ImageView>(R.id.imageViewAvatar)
        if (selectedAvatar != -1) {
            avatarImageView.setImageResource(selectedAvatar)
        }

        val levels = listOf(
            findViewById<Button>(R.id.btnNivel1) to 1,
            findViewById<Button>(R.id.btnNivel2) to 2,
            findViewById<Button>(R.id.btnNivel3) to 3
                           )

        levels.forEach { (button, level) ->
            button.setOnClickListener {
                openRecipeSelector(level, selectedAvatar)
            }
        }

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun openRecipeSelector(level: Int, avatar: Int) {
        val intent = Intent(this, RecipeSelector::class.java)
        intent.putExtra("level", level)
        intent.putExtra("avatar_selected", avatar) // Pasar avatar a RecipeSelector
        startActivity(intent)
        @Suppress("DEPRECATION")
        overridePendingTransition(0, 0)
        finish()
    }
}
