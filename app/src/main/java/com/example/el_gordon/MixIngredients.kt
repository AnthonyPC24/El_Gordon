    package com.example.el_gordon
    
    import android.animation.AnimatorListenerAdapter
    import android.animation.AnimatorSet
    import android.animation.ObjectAnimator
    import android.annotation.SuppressLint
    import android.content.Context
    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.view.MotionEvent
    import android.view.View
    import android.widget.Button
    import android.widget.ImageView
    import androidx.appcompat.app.AppCompatActivity
    import androidx.constraintlayout.widget.ConstraintLayout
    import com.example.el_gordon.data.GameData
    import com.example.el_gordon.data.RecipeData
    import com.tuapp.utils.hideSystemUI
    import java.text.SimpleDateFormat
    import java.util.Date
    import java.util.Locale
    import kotlin.math.cos
    import kotlin.math.sin
    
    class MixIngredients : AppCompatActivity() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        val startTime = dateFormat.format(Date())
        private val plates = mutableListOf<ImageView>()
        private val ingredientsInPlay = mutableListOf<ImageView>()
        private var wrongIngredientsCount = 0
        private lateinit var badIngredients: List<Int>
        private var difficulty = 1
        private val correctIngredientsInPlay = mutableListOf<Int>()
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_mix_ingredients)
            hideSystemUI()
    
            // --- NUEVO: mostrar avatar seleccionado ---
            val avatarView = findViewById<ImageView>(R.id.imageViewAvatar)
            val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val avatarRes = prefs.getInt("selected_avatar", R.drawable.chef_1)
            avatarView.setImageResource(avatarRes)
            // ----------------------------------------
    
            val startTime = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            GameData.player?.fechaHorainicio = startTime
            GameData.startTimeMillis = System.currentTimeMillis()
    
            val recipeIndex = intent.getIntExtra("recipeIndex", 0)
            difficulty = intent.getIntExtra("difficulty", 1)
            val recipeSrc = intent.getIntExtra("drawableRes", 0)
    
            val recipeView = findViewById<ImageView>(R.id.recipe)
            recipeView.setImageResource(recipeSrc)
            recipeView.tag = recipeSrc
    
            val selectedRecipe = RecipeData.getRecipes(this).getOrNull(recipeIndex)
            val allRecipeIngredients = selectedRecipe?.ingredients?.map { it.imageRes } ?: emptyList()
    
            val character = findViewById<View>(R.id.imageViewAvatar)
            val text = findViewById<View>(R.id.text_icon)
            val pot = findViewById<ImageView>(R.id.pot)
            val layout = findViewById<ConstraintLayout>(R.id.constraintLayout)
    
            badIngredients = listOf(
                R.drawable.ing_plant,
                R.drawable.ing_shoe,
                R.drawable.ing_rotten_egg,
                R.drawable.ing_sock,
                R.drawable.ing_rock,
                R.drawable.ing_excrement
            )
    
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
    
                            val numCorrect = when (difficulty) {
                                1 -> 2
                                2 -> 4
                                3 -> 6
                                else -> allRecipeIngredients.size
                            }
                            val correctIngredients = allRecipeIngredients.take(numCorrect)
                            correctIngredientsInPlay.clear()
                            correctIngredientsInPlay.addAll(correctIngredients)
    
                            val numBad = when (difficulty) {
                                1 -> 1
                                2 -> 2
                                3 -> 3
                                else -> 0
                            }
                            val badIngredientsSelected = badIngredients.shuffled().take(numBad)
    
                            val ingredientsToPlay = (correctIngredients + badIngredientsSelected).shuffled()
                            placeIngredientsAroundPot(layout, pot, ingredientsToPlay)
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
                plate.setImageResource(R.drawable.plate)
                plate.layoutParams = ConstraintLayout.LayoutParams(plateSize, plateSize)
                plate.x = (centerX + radius * cos(angle) - plateSize / 2).toFloat()
                plate.y = (centerY + radius * sin(angle) - plateSize / 2).toFloat()
                layout.addView(plate)
                plates.add(plate)
    
                val ingredientSize = 150
                val ingredient = ImageView(this)
                val ingredientResId = ingredientIds[i]
                ingredient.setImageResource(ingredientResId)
                ingredient.layoutParams = ConstraintLayout.LayoutParams(ingredientSize, ingredientSize)
                ingredient.x = plate.x + (plateSize - ingredientSize) / 2
                ingredient.y = plate.y + (plateSize - ingredientSize) / 2
                ingredient.tag = ingredientResId
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
                                    val ingredientResId = v.tag as Int
                                    if (badIngredients.contains(ingredientResId)) {
                                        wrongIngredientsCount++
                                        Log.d("WRONG_INGREDIENTS_COUNT_DEBUG", "$wrongIngredientsCount")
                                    } else {
                                        correctIngredientsInPlay.remove(ingredientResId)
                                    }
    
                                    layout.removeView(v)
                                    ingredientsInPlay.remove(v)
                                    animatePotOnce(pot)
    
                                    // Si todos los ingredientes correctos se agregaron, se eliminan también los malos
                                    if (correctIngredientsInPlay.isEmpty()) {
                                        Log.d("MIX_INGREDIENTS_DEBUG", "Todos los ingredientes correctos agregados")
                                        ingredientsInPlay.forEach { layout.removeView(it) }
                                        ingredientsInPlay.clear()
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
    
        private fun startCaptureAnimation(layout: ConstraintLayout, pot: ImageView, plates: List<ImageView>, repetitions: Int = 3) {
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
                                animateSteam(pot, R.drawable.steam)
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
            val chef = findViewById<ImageView>(R.id.imageViewAvatar)
            val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val baseAvatar = prefs.getInt("selected_avatar", R.drawable.chef_1)
            val avatarName = resources.getResourceEntryName(baseAvatar)

            val cinta = findViewById<ImageView>(R.id.cinta)
            val btnNext = findViewById<Button>(R.id.btn_next)
            val recipe = findViewById<ImageView>(R.id.recipe)
            val gas = findViewById<ImageView>(R.id.backgroundGas) // <<< CAMBIO (imagen gas en tu layout)

            recipe.visibility = View.VISIBLE

            val clampedErrors = wrongIngredientsCount.coerceIn(0, 3)
            val wrongDrawableName = "recipe_wrong$clampedErrors"
            val wrongDrawableId = resources.getIdentifier(wrongDrawableName, "drawable", packageName)

            val angryRes = resources.getIdentifier("${avatarName}_mad", "drawable", packageName)
            val happyRes = resources.getIdentifier("${avatarName}_happy", "drawable", packageName)

            if (wrongDrawableId != 0) {
                recipe.setImageResource(wrongDrawableId)
                cinta.setImageResource(R.drawable.cinta_malasuerte)
                chef.setImageResource(angryRes)
            } else {
                cinta.setImageResource(R.drawable.cinta_enhorabuena3)
                chef.setImageResource(happyRes)
            }

            // --------------------------------------------------------
            // SELECCIÓN DE EFECTO SEGÚN INGREDIENTES MALOS
            // --------------------------------------------------------
            val effectView: ImageView = if (wrongIngredientsCount != 0) {
                gas.setImageResource(R.drawable.toxic_gas)   // <<< CAMBIO
                gas.visibility = View.VISIBLE
                star.visibility = View.INVISIBLE
                gas
            } else {
                star.visibility = View.VISIBLE
                gas.visibility = View.INVISIBLE
                star
            }
            // --------------------------------------------------------

            recipe.scaleX = 0f
            recipe.scaleY = 0f
            effectView.scaleX = 0f
            effectView.scaleY = 0f

            val slowBackX = ObjectAnimator.ofFloat(pot, "scaleX", 1f, 0.9f).apply { duration = 600 }
            val slowBackY = ObjectAnimator.ofFloat(pot, "scaleY", 1f, 0.9f).apply { duration = 600 }

            val effectGrowX = ObjectAnimator.ofFloat(effectView, "scaleX", 0f, 1.5f).apply { duration = 250 }
            val effectGrowY = ObjectAnimator.ofFloat(effectView, "scaleY", 0f, 1.5f).apply { duration = 250 }

            val settleRecipeX = ObjectAnimator.ofFloat(recipe, "scaleX", 0f, 2f).apply { duration = 250 }
            val settleRecipeY = ObjectAnimator.ofFloat(recipe, "scaleY", 0f, 2f).apply { duration = 250 }

            val explosionSet = AnimatorSet().apply {
                playTogether(effectGrowX, effectGrowY, settleRecipeX, settleRecipeY)
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: android.animation.Animator) {
                        pot.visibility = View.INVISIBLE
                        cinta.visibility = View.VISIBLE
                    }
                })
            }

            val potSet = AnimatorSet()
            potSet.playSequentially(
                AnimatorSet().apply { playTogether(slowBackX, slowBackY) },
                explosionSet
            )

            // --------------------------------------------------------
            // ANIMACIONES POST-EXPLOSIÓN
            // SI HAY 1 MALO: NO HACEMOS ROTACIÓN
            // --------------------------------------------------------
            potSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {

                    if (wrongIngredientsCount != 0) {
                        val pulseX = ObjectAnimator.ofFloat(effectView, "scaleX", 1.5f, 1.6f, 1.5f).apply {
                            duration = 1500
                            repeatCount = ObjectAnimator.INFINITE
                            repeatMode = ObjectAnimator.REVERSE
                        }
                        val pulseY = ObjectAnimator.ofFloat(effectView, "scaleY", 1.5f, 1.6f, 1.5f).apply {
                            duration = 1500
                            repeatCount = ObjectAnimator.INFINITE
                            repeatMode = ObjectAnimator.REVERSE
                        }

                        AnimatorSet().apply { playTogether(pulseX, pulseY) }.start()
                        return
                    }

                    val rotate = ObjectAnimator.ofFloat(effectView, "rotation", 0f, 3600f).apply {
                        duration = 30000
                        repeatCount = ObjectAnimator.INFINITE
                    }
                    val pulseX = ObjectAnimator.ofFloat(effectView, "scaleX", 1f, 1.1f, 1f).apply {
                        duration = 1000
                        repeatCount = ObjectAnimator.INFINITE
                        repeatMode = ObjectAnimator.REVERSE
                    }
                    val pulseY = ObjectAnimator.ofFloat(effectView, "scaleY", 1f, 1.1f, 1f).apply {
                        duration = 1000
                        repeatCount = ObjectAnimator.INFINITE
                        repeatMode = ObjectAnimator.REVERSE
                    }

                    val pulseCX = ObjectAnimator.ofFloat(cinta, "scaleX", 1f, 1.1f, 1f).apply {
                        duration = 1500
                        repeatCount = ObjectAnimator.INFINITE
                        repeatMode = ObjectAnimator.REVERSE
                    }
                    val pulseCY = ObjectAnimator.ofFloat(cinta, "scaleX", 1f, 1.1f, 1f).apply {
                        duration = 1500
                        repeatCount = ObjectAnimator.INFINITE
                        repeatMode = ObjectAnimator.REVERSE
                    }

                    AnimatorSet().apply { playTogether(rotate, pulseX, pulseY, pulseCX, pulseCY) }.start()
                }
            })
            // --------------------------------------------------------

            potSet.start()

            btnNext.visibility = View.VISIBLE
            btnNext.setOnClickListener {
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("wrongIngredientsCount", wrongIngredientsCount)
                intent.putExtra("difficulty", difficulty)
                startActivity(intent)
                @Suppress("DEPRECATION")
                overridePendingTransition(0, 0)
                finish()
            }
        }
    }