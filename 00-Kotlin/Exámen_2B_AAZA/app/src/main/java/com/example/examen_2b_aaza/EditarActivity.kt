package com.example.deber02_2b_aaza

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarActivity : AppCompatActivity() {

    private var itemId: Int = 0
    private var tipo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        itemId = intent.getIntExtra("itemId", 0)
        tipo = intent.getStringExtra("tipo") ?: ""

        val editText = findViewById<EditText>(R.id.editTextNombre)
        val editText2 = findViewById<EditText>(R.id.editTextNroEstudiantes)
        val chBox = findViewById<CheckBox>(R.id.editTextEsPrivada)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        if (tipo == "carrera") {
            chBox.visibility = View.INVISIBLE
            editText.setText(intent.getStringExtra("nombre"))
            editText2.hint = "Nuevo nro estudiantes"
            editText2.setText(intent.getIntExtra("nroEstudiantes", 0).toString())
        } else {
            editText.setText(intent.getStringExtra("nombre"))
            editText2.hint = "Nuevo presupuesto"
            editText2.setText(intent.getIntExtra("presupuestoAnual", 0).toString())
            chBox.hint = "Es privada?"
            chBox.setText("Es privada?")
        }

        btnGuardar.setOnClickListener {
            val nuevoNombre = editText.text.toString()
            if (tipo == "universidad") {
                val nuevoPresupuesto = editText2.text.toString().toIntOrNull() ?: 0
                val esPrivada = chBox.isChecked  // Esto devuelve true si está marcado, false si no
                Repositorio.editarNombreUniversidad(this, itemId, nuevoNombre, nuevoPresupuesto, esPrivada)
            } else {
                val nuevoNroEstudiantes = editText2.text.toString().toIntOrNull() ?: 0
                Repositorio.editarCarrera(this, itemId, nuevoNombre, nuevoNroEstudiantes)
            }
            finish()
        }
    }

}
