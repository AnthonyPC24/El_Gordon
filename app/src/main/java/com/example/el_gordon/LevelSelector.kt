package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LevelSelector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selector)

        val btnSelect = findViewById<Button>(R.id.btnNivel1)

        btnSelect.setOnClickListener {
            val intent = Intent(this, RecipeSelector::class.java)
            startActivity(intent)
            finish()
        }
    }
}