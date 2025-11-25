package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI
import android.widget.Button
import android.widget.TextView


class RecipeSelector : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_selector)
        hideSystemUI()

        val btn_select_level = findViewById<TextView>(R.id.tvRecetas)
        val level = intent.getIntExtra("LEVEL", 1)

        btn_select_level.setOnClickListener {
            val intent = Intent(this, MixIngredients::class.java)
            intent.putExtra("LEVEL", level) // Pasamos el nivel
            startActivity(intent)
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
            finish()
        }
        val btnBack = findViewById<Button>(R.id.btn_next)
        btnBack.setOnClickListener {
            val intent = Intent(this, LevelSelector::class.java)
            startActivity(intent)
            finish()
        }
    }
}
