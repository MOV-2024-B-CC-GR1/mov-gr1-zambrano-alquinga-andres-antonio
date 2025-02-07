package com.example.deber01_2b_aaza

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class CarrerasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter_c: CarreraAdapter
    private var universidadId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carreras)

        universidadId = intent.getIntExtra("universidadId", 0)

        recyclerView = findViewById(R.id.recyclerViewCarreras)
        val carreras = Repositorio.obtenerCarrerasDeUniversidad(universidadId)
        adapter_c = CarreraAdapter(carreras, ::onCarreraClick)
        recyclerView.adapter = adapter_c
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnAgregarCarrera).setOnClickListener {
            val nuevaCarrera = Carrera(id = (carreras.size + 1), nombre = "Nueva Carrera", universidadId = universidadId)
            Repositorio.agregarCarrera(this, nuevaCarrera)
            // Actualizar los datos del adaptador con la lista actualizada
            adapter_c.actualizarCarreras(Repositorio.obtenerCarrerasDeUniversidad(universidadId))
        }
    }

    private fun onCarreraClick(carrera: Carrera) {
        val options = arrayOf("Editar", "Eliminar")
        AlertDialog.Builder(this)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editarCarrera(carrera)
                    1 -> eliminarCarrera(carrera)
                }
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        // Actualizar la lista de carreras al regresar
        adapter_c.actualizarCarreras(Repositorio.obtenerCarrerasDeUniversidad(universidadId))
    }

    private fun editarCarrera(carrera: Carrera) {
        val intent = Intent(this, EditarActivity::class.java)
        intent.putExtra("itemId", carrera.id)
        intent.putExtra("tipo", "carrera")
        startActivity(intent)
    }

    private fun eliminarCarrera(carrera: Carrera) {
        Repositorio.eliminarCarrera(this, carrera.id)
        // Actualizar los datos del adaptador con la lista actualizada
        adapter_c.actualizarCarreras(Repositorio.obtenerCarrerasDeUniversidad(universidadId))
    }
}
