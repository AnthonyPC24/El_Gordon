package com.example.el_gordon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class AvatarSelector : AppCompatActivity() {

    private val images = listOf(
        R.drawable.chef_1,
        R.drawable.chef_2,
        R.drawable.chef_3,
        R.drawable.chef_4)

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar_selector)
        hideSystemUI()

        val avatarRes = intent.getIntExtra("avatar_selected", -1)
        // En AvatarSelector.kt
        val playerName = intent.getStringExtra("PLAYER_NAME")

        val imageCarousel = findViewById<ImageView>(R.id.imageCarousel)
        val btnLeft = findViewById<ImageButton>(R.id.btnLeft)
        val btnRight = findViewById<ImageButton>(R.id.btnRight)
        val btnSelect = findViewById<Button>(R.id.btnSelect)

        val avatarView = findViewById<ImageView>(R.id.imageViewAvatar)

        if (avatarRes != -1) {
            avatarView.setImageResource(avatarRes)
        }


        imageCarousel.setImageResource(images[currentIndex])

        btnLeft.setOnClickListener {
            currentIndex = if (currentIndex - 1 < 0) {
                images.size - 1
            } else {
                currentIndex - 1
            }
            imageCarousel.setImageResource(images[currentIndex])
        }

        btnRight.setOnClickListener {
            currentIndex = (currentIndex + 1) % images.size
            imageCarousel.setImageResource(images[currentIndex])
        }

        btnSelect.setOnClickListener {
            val selectedAvatar = images[currentIndex]

            // Guardar avatar en SharedPreferences (opcional)
            val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            prefs.edit().putInt("selected_avatar", selectedAvatar).apply()

            // Enviar avatar seleccionado a LevelSelector
            val intent = Intent(this, LevelSelector::class.java)
            intent.putExtra("avatar_selected", selectedAvatar)
            startActivity(intent)
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
            finish()
        }

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
