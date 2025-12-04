package com.example.el_gordon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class LevelSelector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selector)
        hideSystemUI()

        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val selectedAvatar = prefs.getInt("selected_avatar", R.drawable.chef_1)
        val avatarImageView = findViewById<ImageView>(R.id.imageViewAvatar)
        avatarImageView.setImageResource(selectedAvatar)

        val levels = listOf(
            findViewById<LinearLayout>(R.id.btnNivel1) to 1,
            findViewById<LinearLayout>(R.id.btnNivel2) to 2,
            findViewById<LinearLayout>(R.id.btnNivel3) to 3
        )

        levels.forEach { (button, level) ->
            button.setOnClickListener {
                openRecipeSelector(level, selectedAvatar)
            }
        }

        val btnBack = findViewById<Button>(R.id.btnBackAvatarSel)
        btnBack.setOnClickListener {
            val intent = Intent(this, AvatarSelector::class.java)
            startActivity(intent)
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
            finish()
        }
    }

    private fun openRecipeSelector(level: Int, avatar: Int) {
        val intent = Intent(this, RecipeSelector::class.java)
        intent.putExtra("level", level)
        intent.putExtra("avatar_selected", avatar)
        startActivity(intent)
        @Suppress("DEPRECATION")
        overridePendingTransition(0, 0)
        finish()
    }
}
