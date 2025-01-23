package com.example.deber01_2b_aaza

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UniversidadesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UniversidadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universidades)


        recyclerView = findViewById(R.id.recyclerView)
        adapter = UniversidadAdapter(Repositorio.universidades, ::onUniversidadClick)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnAgregarUniversidad).setOnClickListener {
            // Agregar universidad con un diálogo o formulario
            val id = (Repositorio.universidades.size + 1)
            val nuevaUniversidad = Universidad(id = id, nombre = "Nueva Universidad xd")
            println(id)
            Repositorio.agregarUniversidad(this, nuevaUniversidad)
            adapter.notifyDataSetChanged()
        }
    }

    // Método onResume para recargar la lista de universidades cada vez que regrese a esta actividad
    override fun onResume() {
        super.onResume()

        // Recargar la lista de universidades desde el repositorio
        adapter = UniversidadAdapter(Repositorio.universidades, ::onUniversidadClick)
        recyclerView.adapter = adapter // Volver a asignar el adaptador para reflejar los cambios
        adapter.notifyDataSetChanged()
    }

    private fun onUniversidadClick(universidad: Universidad) {
        val options = arrayOf("Editar", "Eliminar", "Ver carreras")
        AlertDialog.Builder(this)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editarUniversidad(universidad)
                    1 -> eliminarUniversidad(universidad)
                    2 -> verCarreras(universidad)
                }
            }
            .show()
    }

    private fun editarUniversidad(universidad: Universidad) {
        val intent = Intent(this, EditarActivity::class.java)
        intent.putExtra("itemId", universidad.id)
        intent.putExtra("tipo", "universidad")
        startActivity(intent)
    }

    private fun eliminarUniversidad(universidad: Universidad) {
        Repositorio.eliminarUniversidad(this, universidad.id)
        adapter.notifyDataSetChanged()
    }

    private fun verCarreras(universidad: Universidad) {
        val intent = Intent(this, CarrerasActivity::class.java)
        intent.putExtra("universidadId", universidad.id)
        startActivity(intent)
    }
}
