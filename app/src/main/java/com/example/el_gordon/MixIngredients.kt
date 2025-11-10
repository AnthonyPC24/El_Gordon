package com.example.el_gordon

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.tuapp.utils.hideSystemUI
import kotlin.math.cos
import kotlin.math.sin

class MixIngredients : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mix_ingredients)
        hideSystemUI()

        val personaje = findViewById<View>(R.id.imageView)
        val texto = findViewById<View>(R.id.text_icon)
        val olla = findViewById<ImageView>(R.id.pot)
        val layout = findViewById<ConstraintLayout>(R.id.constraintLayout)

        val ingredients = listOf(
            R.drawable.cheese_icon,
            R.drawable.shoe_icon,
            R.drawable.cheese_icon,
            R.drawable.shoe_icon,
            R.drawable.cheese_icon,
            R.drawable.shoe_icon)

        texto.animate()
            .alpha(0f)
            .setStartDelay(3000)
            .setDuration(1000)
            .withEndAction {
                val girarIzq = ObjectAnimator.ofFloat(personaje, "scaleX", 1f)
                girarIzq.duration = 400

                val moverIzq = ObjectAnimator.ofFloat(personaje, "translationX", -350f)
                moverIzq.duration = 1000

                val girarFrente = ObjectAnimator.ofFloat(personaje, "scaleX", -1f)
                girarFrente.duration = 400

                val animSet = AnimatorSet()
                animSet.playSequentially(girarIzq, moverIzq, girarFrente)

                animSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        layout.post {
                            placeIngredientsInCircle(layout, olla, ingredients, radius = 300f, ingredientSizeDp = 150f, offsetX = 100f)
                        }
                    }
                })

                animSet.start()
            }
    }

    private fun placeIngredientsInCircle(
        layout: ConstraintLayout,
        pot: ImageView,
        ingredients: List<Int>,
        radius: Float = 300f,
        ingredientSizeDp: Float = 60f, // tamaño en dp
        offsetX: Float = 100f) {
        val scale = resources.displayMetrics.density
        val ingredientSizePx = (ingredientSizeDp * scale).toInt() // convertir dp a px

        val potCenterX = pot.x + pot.width / 2f + offsetX
        val potCenterY = pot.y + pot.height / 2f

        val n = ingredients.size
        for (i in ingredients.indices) {
            val angle = 360f / n * i
            val rad = Math.toRadians(angle.toDouble())
            val x = (potCenterX + radius * cos(rad) - ingredientSizePx / 2).toFloat()
            val y = (potCenterY + radius * sin(rad) - ingredientSizePx / 2).toFloat()

            val ingredientView = ImageView(this)
            ingredientView.setImageResource(ingredients[i])
            val params = ConstraintLayout.LayoutParams(ingredientSizePx, ingredientSizePx)
            ingredientView.layoutParams = params

            ingredientView.x = x
            ingredientView.y = y

            layout.addView(ingredientView)
        }
    }
}