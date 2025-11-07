package com.example.el_gordon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtJugar = findViewById<TextView>(R.id.txtJugar)
        txtJugar.setOnClickListener {
            val intent = Intent(this, JuegoActivity::class.java)
            startActivity(intent)
        }
    }
}
