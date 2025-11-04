package com.example.el_gordon

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AvatarSelector : AppCompatActivity() {

    // Lista de imágenes del carrusel
    private val images = listOf(
        R.drawable.chef1_icon,
        R.drawable.chef2_icon,
        R.drawable.chef3_icon,
        R.drawable.chef4_icon)

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar_selector)

        val imageCarousel = findViewById<ImageView>(R.id.imageCarousel)
        val btnLeft = findViewById<ImageButton>(R.id.btnLeft)
        val btnRight = findViewById<ImageButton>(R.id.btnRight)

        // Mostrar la primera imagen
        imageCarousel.setImageResource(images[currentIndex])

        // Botón izquierdo
        btnLeft.setOnClickListener {
            currentIndex = if (currentIndex - 1 < 0) {
                images.size - 1
            } else {
                currentIndex - 1
            }
            imageCarousel.setImageResource(images[currentIndex])
        }

        // Botón derecho
        btnRight.setOnClickListener {
            currentIndex = (currentIndex + 1) % images.size
            imageCarousel.setImageResource(images[currentIndex])
        }
    }
}
