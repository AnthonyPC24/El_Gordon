package com.example.el_gordon

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class RecipeSelector : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_selector)
        hideSystemUI()

        val difficulty = intent.getIntExtra("level", 1)
        val grid = findViewById<GridLayout>(R.id.gridRecetas)

        val recipeNames = listOf(
            "recipe_butter_bread",
            "recipe_milkshake",
            "recipe_salad",
            "recipe_pasta",
            "recipe_rice",
            "recipe_omelette"
        )

        recipeNames.forEachIndexed { index, name ->
            val drawableRes = resources.getIdentifier("${name}${difficulty}", "drawable", packageName)

            val imageView = ImageView(this).apply {
                setImageResource(drawableRes)
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(8, 8, 8, 8)
                }
                scaleType = ImageView.ScaleType.FIT_CENTER
                adjustViewBounds = true
                isClickable = true
                isFocusable = true
            }

            grid.addView(imageView)

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

                            intent.putExtra("recipeIndex", index)
                            intent.putExtra("difficulty", difficulty)

                            startActivity(intent)
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