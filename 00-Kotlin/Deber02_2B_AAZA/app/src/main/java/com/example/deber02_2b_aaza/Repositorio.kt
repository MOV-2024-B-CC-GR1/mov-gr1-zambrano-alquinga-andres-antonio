package com.example.deber01_2b_aaza

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter

object Repositorio {
    public val universidades = mutableListOf<Universidad>()
    public val carreras = mutableListOf<Carrera>()

    // Se pasa el contexto para acceder al almacenamiento interno
    fun init(context: Context) {
        cargarDatos(context)
    }

    fun agregarUniversidad(context: Context, universidad: Universidad) {
        universidades.add(universidad)
        guardarDatos(context)
    }

    fun eliminarUniversidad(context: Context, id: Int) {
        universidades.removeAll { it.id == id }
        carreras.removeAll { it.universidadId == id }
        guardarDatos(context)
    }

    fun obtenerCarrerasDeUniversidad(universidadId: Int): List<Carrera> {
        return carreras.filter { it.universidadId == universidadId }
    }

    fun agregarCarrera(context: Context, carrera: Carrera) {
        carreras.add(carrera)
        guardarDatos(context)
    }

    fun eliminarCarrera(context: Context, id: Int) {
        carreras.removeAll { it.id == id }
        guardarDatos(context)
    }

    fun editarNombreUniversidad(context: Context, id: Int, nuevoNombre: String) {
        val universidad = universidades.find { it.id == id }
        universidad?.nombre = nuevoNombre
        guardarDatos(context)
    }

    fun editarNombreCarrera(context: Context, id: Int, nuevoNombre: String) {
        val carrera = carreras.find { it.id == id }
        carrera?.nombre = nuevoNombre
        guardarDatos(context)
    }

    // Guardar los datos en el archivo dentro del almacenamiento interno
    private fun guardarDatos(context: Context) {
        val universidadesJson = JSONArray()
        universidades.forEach { universidad ->
            val universidadJson = JSONObject()
            universidadJson.put("id", universidad.id)
            universidadJson.put("nombre", universidad.nombre)
            universidadesJson.put(universidadJson)
        }

        val carrerasJson = JSONArray()
        carreras.forEach { carrera ->
            val carreraJson = JSONObject()
            carreraJson.put("id", carrera.id)
            carreraJson.put("nombre", carrera.nombre)
            carreraJson.put("universidadId", carrera.universidadId)
            carrerasJson.put(carreraJson)
        }

        // Ruta correcta en el almacenamiento interno
        val file = File(context.filesDir, "data.json")
        val fileWriter = FileWriter(file)
        fileWriter.write("{\"universidades\": $universidadesJson, \"carreras\": $carrerasJson}")
        fileWriter.close()
    }

    // Cargar los datos desde el archivo en el almacenamiento interno
    private fun cargarDatos(context: Context) {
        val file = File(context.filesDir, "data.json")
        if (file.exists()) {
            val jsonString = file.readText()
            val jsonObject = JSONObject(jsonString)
            val universidadesJson = jsonObject.getJSONArray("universidades")
            for (i in 0 until universidadesJson.length()) {
                val universidadJson = universidadesJson.getJSONObject(i)
                universidades.add(
                    Universidad(
                        universidadJson.getInt("id"),
                        universidadJson.getString("nombre")
                    )
                )
            }
            val carrerasJson = jsonObject.getJSONArray("carreras")
            for (i in 0 until carrerasJson.length()) {
                val carreraJson = carrerasJson.getJSONObject(i)
                carreras.add(
                    Carrera(
                        carreraJson.getInt("id"),
                        carreraJson.getString("nombre"),
                        carreraJson.getInt("universidadId")
                    )
                )
            }
        }
    }
}
