package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class RecipeSelector : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_selector)
        hideSystemUI()
<<<<<<< HEAD

        val btnSelect = findViewById<Button>(R.id.tvRecetas)
        val level = intent.getIntExtra("LEVEL", 1)

        btnSelect.setOnClickListener {
            val intent = Intent(this, MixIngredients::class.java)
            intent.putExtra("LEVEL", level) // Pasamos el nivel
            startActivity(intent)
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
            finish()
        }
=======
>>>>>>> origin/daniil
    }
}
