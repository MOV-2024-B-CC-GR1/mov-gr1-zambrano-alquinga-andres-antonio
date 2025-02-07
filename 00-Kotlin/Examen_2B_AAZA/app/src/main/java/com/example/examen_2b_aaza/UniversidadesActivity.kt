package com.example.examen_2b_aaza

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
        adapter = UniversidadAdapter(Repositorio.obtenerUniversidades(), ::onUniversidadClick)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnAgregarCarrera).setOnClickListener {
            val id = (Repositorio.obtenerUniversidades().size + 1)
            val nombre = "Nueva Universidad"
            val presupuesto = 500000
            val nuevaUniversidad = Universidad(id = id, nombre = nombre, presupuestoAnual = presupuesto, esPrivada = false)
            Repositorio.agregarUniversidad(this, nuevaUniversidad)
            adapter.actualizarDatos(Repositorio.obtenerUniversidades())
            adapter.notifyDataSetChanged()
        }
    }

    // Método onResume para recargar la lista de universidades cada vez que regrese a esta actividad
    override fun onResume() {
        super.onResume()

        // Recargar la lista de universidades desde el repositorio
        adapter = UniversidadAdapter(Repositorio.obtenerUniversidades(), ::onUniversidadClick)
        recyclerView.adapter = adapter // Volver a asignar el adaptador para reflejar los cambios
        adapter.notifyDataSetChanged()
    }

    private fun onUniversidadClick(universidad: Universidad) {
        val options = arrayOf("Editar", "Eliminar", "Ver carreras", "Mapa")
        AlertDialog.Builder(this)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editarUniversidad(universidad)
                    1 -> eliminarUniversidad(universidad)
                    2 -> verCarreras(universidad)
                    3 -> irMapa(universidad)
                }
            }
            .show()
    }

    private fun editarUniversidad(universidad: Universidad) {
        val intent = Intent(this, EditarActivity::class.java)
        intent.putExtra("itemId", universidad.id)
        intent.putExtra("nombre", universidad.nombre)
        intent.putExtra("presupuestoAnual", universidad.presupuestoAnual)
        intent.putExtra("esPrivada", universidad.esPrivada)
        intent.putExtra("tipo", "universidad")
        startActivity(intent)
    }

    private fun eliminarUniversidad(universidad: Universidad) {
        Repositorio.eliminarUniversidad(this, universidad.id)
        adapter.notifyDataSetChanged()
        adapter.actualizarDatos(Repositorio.obtenerUniversidades())
    }

    private fun verCarreras(universidad: Universidad) {
        val intent = Intent(this, CarrerasActivity::class.java)
        intent.putExtra("universidadId", universidad.id)
        startActivity(intent)
    }

    private fun irMapa(universidad: Universidad) {
        try {
            val intent = Intent(this, GMaps::class.java)
            intent.putExtra("departamento_id", universidad.id)
            intent.putExtra("departamento_nombre", universidad.nombre)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al abrir el mapa: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }


}
