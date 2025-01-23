package com.example.deber01_2b_aaza

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarActivity : AppCompatActivity() {

    private var itemId: Int = 0
    private var tipo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)
        //Repositorio.init(this)

        itemId = intent.getIntExtra("itemId", 0)
        tipo = intent.getStringExtra("tipo") ?: ""

        val editText = findViewById<EditText>(R.id.editTextNombre)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nuevoNombre = editText.text.toString()
            if (tipo == "universidad") {
                Repositorio.editarNombreUniversidad(this, itemId, nuevoNombre)
            } else {
                Repositorio.editarNombreCarrera(this, itemId, nuevoNombre)
            }
            finish()
        }
    }

}
