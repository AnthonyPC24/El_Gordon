package com.example.el_gordon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.el_gordon.AvatarSelector
import com.example.el_gordon.R

class PlayerNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_name)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val btnComenzar = findViewById<Button>(R.id.btnComenzar)

        btnComenzar.setOnClickListener {
            val nombre = etNombre.text.toString()

            if (nombre.isNotEmpty()) {
                // Pasar el nombre al AvatarSelector
                val intent = Intent(this, AvatarSelector::class.java)
                intent.putExtra("PLAYER_NAME", nombre)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
}