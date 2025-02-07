package com.example.deber02_2b_aaza

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Redirigir directamente a la actividad UniversidadesActivity
        val intent = Intent(this, UniversidadesActivity::class.java)
        Repositorio.init(this)
        startActivity(intent)
        finish()
    }
}
