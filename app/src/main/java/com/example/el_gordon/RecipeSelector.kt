package com.example.el_gordon

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class RecipeSelector : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_selector)
        hideSystemUI()

        val difficulty = intent.getIntExtra("level", 0)

        val recipeNames = listOf(
            "recipe_butter_bread",
            "recipe_milkshake",
            "recipe_pasta",
            "recipe_rice",
            "recipe_salad",
            "recipe_omelette",
        )

        val selectedRecipe = recipeNames.map { name ->
            val id = resources.getIdentifier("${name}${difficulty}", "id", packageName)
            findViewById<ImageView>(id)
        }

        selectedRecipe.forEach { imageView ->
            imageView.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.scaleX = 0.9f
                        v.scaleY = 0.9f
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        v.scaleX = 1f
                        v.scaleY = 1f

                        if (event.action == MotionEvent.ACTION_UP) {
                            val intent = Intent(this, MixIngredients::class.java)
                            intent.putExtra("id_recipe", imageView.id)
                            startActivity(intent)
                            @Suppress("DEPRECATION")
                            overridePendingTransition(0, 0)
                            finish()
                        }
                    }
                }
                true
            }
        }
    }
}
