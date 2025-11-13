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

        val btnSelect = findViewById<Button>(R.id.btnNivel1)

        btnSelect.setOnClickListener {
            val intent = Intent(this, RecipeSelector::class.java)

            startActivity(intent)
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
            finish()
        }
    }
}