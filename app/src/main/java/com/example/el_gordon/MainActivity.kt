package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.view.animation.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.utils.hideSystemUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideSystemUI()

        val tvJugar = findViewById<TextView>(R.id.tvJugar)

        // 🔹 Animación de pulso infinito
        val pulse = ScaleAnimation(
            1f, 1.1f, // escala X
            1f, 1.1f, // escala Y
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
                                  )
        pulse.duration = 500
        pulse.repeatCount = Animation.INFINITE
        pulse.repeatMode = Animation.REVERSE
        tvJugar.startAnimation(pulse)

        // 🔹 Rebote al pulsar
        tvJugar.setOnClickListener {
            val bounce = ScaleAnimation(
                1f, 0.8f,
                1f, 0.8f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
                                       )
            bounce.duration = 100
            bounce.interpolator = BounceInterpolator()
            bounce.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    // Abrir AvatarSelector después del rebote
                    startActivity(Intent(this@MainActivity, AvatarSelector::class.java))
                    finish()
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            tvJugar.startAnimation(bounce)
        }
    }
}
