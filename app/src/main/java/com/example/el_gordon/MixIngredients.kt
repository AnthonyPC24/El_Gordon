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

        val character = findViewById<View>(R.id.imageView)
        val text = findViewById<View>(R.id.text_icon)
        val pot = findViewById<ImageView>(R.id.pot)
        val layout = findViewById<ConstraintLayout>(R.id.constraintLayout)

        val ingredients = listOf(
            R.drawable.cheese_icon,
            R.drawable.shoe_icon,
            R.drawable.cheese_icon,
            R.drawable.shoe_icon,
            R.drawable.cheese_icon,
            R.drawable.shoe_icon,
            R.drawable.cheese_icon)

        text.animate()
            .alpha(0f)
            .setStartDelay(3000)
            .setDuration(1000)
            .withEndAction {
                val girarIzq = ObjectAnimator.ofFloat(character, "scaleX", 1f).apply { duration = 400 }
                val moverIzq = ObjectAnimator.ofFloat(character, "translationX", -400f).apply { duration = 1000 }
                val girarFrente = ObjectAnimator.ofFloat(character, "scaleX", -1f).apply { duration = 400 }

                val animSet = AnimatorSet()
                animSet.playSequentially(girarIzq, moverIzq, girarFrente)

                animSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: android.animation.Animator) {
                        super.onAnimationEnd(animation)

                        pot.visibility = View.VISIBLE
                        placeIngredientsAroundPot(layout, pot, ingredients)

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
        repetitions: Int = 3) {
        var count = 0

        fun animateOnce() {
            if (count >= repetitions) return

            for (plate in plates) {
                plate.animate().alpha(0f).setDuration(200).start()
            }

            pot.post {
                pot.pivotX = pot.width / 2f
                pot.pivotY = pot.height / 2f

                val rotateRight = ObjectAnimator.ofFloat(pot, "rotation", 0f, 20f).apply { duration = 175 }
                val rotateLeft = ObjectAnimator.ofFloat(pot, "rotation", 20f, -20f).apply { duration = 275 }
                val rotateCenter = ObjectAnimator.ofFloat(pot, "rotation", -20f, 0f).apply { duration = 175 }

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
                            animateSteam(pot, R.drawable.steam_icon)
                        }
                    }
                })
                animSet.start()
            }
        }

        animateOnce()
    }

    private fun animateSteam(pot: ImageView, steamResId: Int) {
        val steam = findViewById<ImageView>(R.id.steam)
        val star = findViewById<ImageView>(R.id.backgroundStar)

        steam.setImageResource(steamResId)
        steam.visibility = View.VISIBLE

        val moveUp = ObjectAnimator.ofFloat(steam, "translationY", 0f, -200f).apply { duration = 2000 }
        val fadeOut = ObjectAnimator.ofFloat(steam, "alpha", 1f, 0f).apply { duration = 2000 }

        val animSet = AnimatorSet()
        animSet.playTogether(moveUp, fadeOut)
        animSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                super.onAnimationEnd(animation)
                steam.visibility = View.INVISIBLE
                steam.alpha = 0.5f
                steam.translationY = 0f

                animateStarWithPotExplosion(pot, star)
            }
        })
        animSet.start()
    }


    private fun animateStarWithPotExplosion(pot: ImageView, star: ImageView) {
        // 1️⃣ Retroceso lento de la olla
        val slowBackX = ObjectAnimator.ofFloat(pot, "scaleX", 1f, 0.9f).apply { duration = 600 }
        val slowBackY = ObjectAnimator.ofFloat(pot, "scaleY", 1f, 0.9f).apply { duration = 600 }

        // 2️⃣ Expansión rápida (boom) de la olla
        val recipe = findViewById<ImageView>(R.id.recipe)
        val quickExpandX = ObjectAnimator.ofFloat(recipe, "scaleX", 1.4f).apply { duration = 250 }
        val quickExpandY = ObjectAnimator.ofFloat(recipe, "scaleY", 1.4f).apply { duration = 250 }

        // Sincronizar estrella con la expansión de la olla
        star.visibility = View.VISIBLE
        star.scaleX = 0f
        star.scaleY = 0f
        val starGrowX = ObjectAnimator.ofFloat(star, "scaleX", 1f).apply { duration = 250 }
        val starGrowY = ObjectAnimator.ofFloat(star, "scaleY", 1f).apply { duration = 250 }

        // 3️⃣ Vuelve ligeramente al tamaño normal
        val settleRecipeX = ObjectAnimator.ofFloat(recipe, "scaleX", 1.2f).apply { duration = 200 }
        val settleRecipeY = ObjectAnimator.ofFloat(recipe, "scaleY", 1.2f).apply { duration = 200 }
        recipe.visibility = View.VISIBLE
        pot.visibility = View.INVISIBLE
        // Combinar secuencias
        val potSet = AnimatorSet()
        potSet.playSequentially(
            AnimatorSet().apply { playTogether(slowBackX, slowBackY) },
            AnimatorSet().apply { playTogether(quickExpandX, quickExpandY, starGrowX, starGrowY) },
            AnimatorSet().apply { playTogether(settleRecipeX, settleRecipeY) })

        potSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                super.onAnimationEnd(animation)
                val rotate = ObjectAnimator.ofFloat(star, "rotation", 0f, 360f).apply {
                    duration = 3000
                    repeatCount = ObjectAnimator.INFINITE
                    repeatMode = ObjectAnimator.RESTART
                }
                val pulseX = ObjectAnimator.ofFloat(star, "scaleX", 1f, 1.1f, 1f).apply {
                    duration = 1000
                    repeatCount = ObjectAnimator.INFINITE
                    repeatMode = ObjectAnimator.REVERSE
                }
                val pulseY = ObjectAnimator.ofFloat(star, "scaleY", 1f, 1.1f, 1f).apply {
                    duration = 1000
                    repeatCount = ObjectAnimator.INFINITE
                    repeatMode = ObjectAnimator.REVERSE
                }
                AnimatorSet().apply { playTogether(rotate, pulseX, pulseY) }.start()
            }
        })

        potSet.start()
    }
}