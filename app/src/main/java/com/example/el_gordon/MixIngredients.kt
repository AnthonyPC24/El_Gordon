package com.example.el_gordon

import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.tuapp.utils.hideSystemUI
import kotlin.math.cos
import kotlin.math.sin

class MixIngredients : AppCompatActivity() {

    private val plates = mutableListOf<ImageView>()
    private val ingredientsInPlay = mutableListOf<ImageView>()

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
            R.drawable.shoe_icon
                                )

        // Animación inicial del personaje
        texto.animate()
            .alpha(0f)
            .setStartDelay(3000)
            .setDuration(1000)
            .withEndAction {
                val girarIzq = ObjectAnimator.ofFloat(personaje, "scaleX", 1f).apply { duration = 400 }
                val moverIzq = ObjectAnimator.ofFloat(personaje, "translationX", -400f).apply { duration = 1000 }
                val girarFrente = ObjectAnimator.ofFloat(personaje, "scaleX", -1f).apply { duration = 400 }

                val animSet = AnimatorSet()
                animSet.playSequentially(girarIzq, moverIzq, girarFrente)

                animSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: android.animation.Animator) {
                        super.onAnimationEnd(animation)
                        olla.postDelayed({
                            olla.visibility = View.VISIBLE
                            placeIngredientsAroundPot(layout, olla, ingredients)
                                         }, 250)
                    }
                })

                animSet.start()
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun placeIngredientsAroundPot(layout: ConstraintLayout, pot: ImageView, ingredientIds: List<Int>) {
        val centerX = pot.x + pot.width / 2
        val centerY = pot.y + pot.height / 2
        val radius = pot.width * 1.25

        for (i in ingredientIds.indices) {
            val angle = 2 * Math.PI * i / ingredientIds.size

            val plateSize = 200
            val plate = ImageView(this)
            plate.setImageResource(R.drawable.plate_icon)
            plate.layoutParams = ConstraintLayout.LayoutParams(plateSize, plateSize)
            plate.x = (centerX + radius * cos(angle) - plateSize / 2).toFloat()
            plate.y = (centerY + radius * sin(angle) - plateSize / 2).toFloat()
            layout.addView(plate)
            plates.add(plate)

            val ingredientSize = 150
            val ingredient = ImageView(this)
            ingredient.setImageResource(ingredientIds[i])
            ingredient.layoutParams = ConstraintLayout.LayoutParams(ingredientSize, ingredientSize)
            ingredient.x = plate.x + (plateSize - ingredientSize) / 2
            ingredient.y = plate.y + (plateSize - ingredientSize) / 2
            layout.addView(ingredient)
            ingredientsInPlay.add(ingredient)

            ingredient.setOnTouchListener(object : View.OnTouchListener {
                var dX = 0f
                var dY = 0f

                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            dX = v.x - event.rawX
                            dY = v.y - event.rawY
                        }
                        MotionEvent.ACTION_MOVE -> {
                            v.x = event.rawX + dX
                            v.y = event.rawY + dY
                        }
                        MotionEvent.ACTION_UP -> {
                            v.performClick()

                            val ingredientCenterX = v.x + v.width / 2
                            val ingredientCenterY = v.y + v.height / 2
                            val potLeft = pot.x
                            val potRight = pot.x + pot.width
                            val potTop = pot.y
                            val potBottom = pot.y + pot.height

                            if (ingredientCenterX in potLeft..potRight && ingredientCenterY in potTop..potBottom) {
                                layout.removeView(v)
                                ingredientsInPlay.remove(v)

                                animatePotOnce(pot)

                                if (ingredientsInPlay.isEmpty()) {
                                    startCaptureAnimation(layout, pot, plates)
                                }
                            } else {
                                v.animate()
                                    .x(plate.x + (plateSize - ingredientSize) / 2)
                                    .y(plate.y + (plateSize - ingredientSize) / 2)
                                    .setDuration(300)
                                    .start()
                            }
                        }
                    }
                    return true
                }
            })
        }
    }

    private fun animatePotOnce(pot: ImageView) {
        pot.post {
            pot.pivotX = pot.width / 2f
            pot.pivotY = pot.height / 2f

            val rotateRight = ObjectAnimator.ofFloat(pot, "rotation", 0f, 15f).apply { duration = 150 }
            val rotateLeft = ObjectAnimator.ofFloat(pot, "rotation", 15f, -15f).apply { duration = 300 }
            val rotateCenter = ObjectAnimator.ofFloat(pot, "rotation", -15f, 0f).apply { duration = 150 }

            val animSet = AnimatorSet()
            animSet.playSequentially(rotateRight, rotateLeft, rotateCenter)
            animSet.start()
        }
    }

    private fun startCaptureAnimation(
        layout: ConstraintLayout,
        pot: ImageView,
        plates: List<ImageView>,
        repetitions: Int = 3
                                     ) {
        var count = 0

        fun animateOnce() {
            if (count >= repetitions) return

            for (plate in plates) {
                plate.animate().alpha(0f).setDuration(200).start()
            }

            pot.post {
                pot.pivotX = pot.width / 2f
                pot.pivotY = pot.height / 2f

                val rotateRight = ObjectAnimator.ofFloat(pot, "rotation", 0f, 20f).apply { duration = 100 }
                val rotateLeft = ObjectAnimator.ofFloat(pot, "rotation", 20f, -20f).apply { duration = 200 }
                val rotateCenter = ObjectAnimator.ofFloat(pot, "rotation", -20f, 0f).apply { duration = 100 }

                val jumpUp = ObjectAnimator.ofFloat(pot, "translationY", 0f, -30f).apply { duration = 100 }
                val jumpDown = ObjectAnimator.ofFloat(pot, "translationY", -30f, 0f).apply { duration = 100 }

                val animSet = AnimatorSet()
                animSet.playSequentially(rotateRight, rotateLeft, rotateCenter, jumpUp, jumpDown)
                animSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: android.animation.Animator) {
                        super.onAnimationEnd(animation)
                        count++
                        if (count < repetitions) {
                            animateOnce()
                        } else {
                            animateSteam(layout, pot, R.drawable.steam_icon)
                        }
                    }
                })
                animSet.start()
            }
        }

        animateOnce()
    }

    private fun animateSteam(layout: ConstraintLayout, pot: ImageView, steamRes: Int) {
        val steam = ImageView(this)
        steam.setImageResource(steamRes)
        val size = 100
        steam.layoutParams = ConstraintLayout.LayoutParams(size, size)

        // Posición inicial encima de la olla
        steam.x = pot.x + pot.translationX + pot.width / 2f - size / 2f
        steam.y = pot.y + pot.translationY - size
        layout.addView(steam)

        val moveUp = ObjectAnimator.ofFloat(steam, "translationY", 0f, -200f).apply { duration = 2000 }
        val moveRight = ObjectAnimator.ofFloat(steam, "translationX", 0f, 30f).apply { duration = 500 }
        val moveLeft = ObjectAnimator.ofFloat(steam, "translationX", 30f, -30f).apply { duration = 500 }
        val moveCenter = ObjectAnimator.ofFloat(steam, "translationX", -30f, 0f).apply { duration = 500 }

        val horizontalAnim = AnimatorSet()
        horizontalAnim.playSequentially(moveRight, moveLeft, moveCenter)

        val fullAnim = AnimatorSet()
        fullAnim.playTogether(moveUp, horizontalAnim)
        fullAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                layout.removeView(steam)
            }
        })

        fullAnim.start()
    }
}