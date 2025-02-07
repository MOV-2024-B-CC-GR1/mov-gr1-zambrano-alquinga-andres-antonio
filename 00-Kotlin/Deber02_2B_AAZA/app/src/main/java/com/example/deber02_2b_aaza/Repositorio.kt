package com.example.deber02_2b_aaza

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

object Repositorio {
    private lateinit var dbHelper: DBHelper

    fun init(context: Context) {
        dbHelper = DBHelper(context)
    }

    fun agregarUniversidad(context: Context, universidad: Universidad): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COL_UNI_NOMBRE, universidad.nombre)
            put(DBHelper.COL_UNI_PRESUPUESTO, universidad.presupuestoAnual)
            put(DBHelper.COL_UNI_ESPRIVADA, if (universidad.esPrivada) 1 else 0)
        }
        return db.insert(DBHelper.TABLE_UNIVERSIDADES, null, values)
    }

    fun obtenerUniversidades(): List<Universidad> {
        val universidades = mutableListOf<Universidad>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_UNIVERSIDADES,
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val universidad = Universidad(
                    getInt(getColumnIndexOrThrow(DBHelper.COL_UNI_ID)),
                    getString(getColumnIndexOrThrow(DBHelper.COL_UNI_NOMBRE)),
                    getInt(getColumnIndexOrThrow(DBHelper.COL_UNI_PRESUPUESTO)),
                    getInt(getColumnIndexOrThrow(DBHelper.COL_UNI_ESPRIVADA)) == 1
                )
                universidades.add(universidad)
            }
        }
        cursor.close()
        return universidades
    }

    fun eliminarUniversidad(context: Context, id: Int) {
        val db = dbHelper.writableDatabase
        db.delete(DBHelper.TABLE_UNIVERSIDADES, "${DBHelper.COL_UNI_ID} = ?", arrayOf(id.toString()))
    }

    fun obtenerCarrerasDeUniversidad(uniId: Int): List<Carrera> {
        val carreras = mutableListOf<Carrera>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_CARRERAS,
            null,
            "${DBHelper.COL_CARR_UNI_ID} = ?",
            arrayOf(uniId.toString()),
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val carrera = Carrera(
                    getInt(getColumnIndexOrThrow(DBHelper.COL_CARR_ID)),
                    getString(getColumnIndexOrThrow(DBHelper.COL_CARR_NOMBRE)),
                    getInt(getColumnIndexOrThrow(DBHelper.COL_CARR_NRO_ESTUDIANTES)),
                    getInt(getColumnIndexOrThrow(DBHelper.COL_CARR_UNI_ID))
                )
                carreras.add(carrera)
            }
        }
        cursor.close()
        return carreras
    }

    fun agregarCarrera(context: Context, carrera: Carrera): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COL_CARR_NOMBRE, carrera.nombre)
            put(DBHelper.COL_CARR_NRO_ESTUDIANTES, carrera.nroEstudiantes)
            put(DBHelper.COL_CARR_UNI_ID, carrera.uniId)
        }
        return db.insert(DBHelper.TABLE_CARRERAS, null, values)
    }

    fun eliminarCarrera(context: Context, id: Int) {
        val db = dbHelper.writableDatabase
        db.delete(DBHelper.TABLE_CARRERAS, "${DBHelper.COL_CARR_ID} = ?", arrayOf(id.toString()))
    }

    fun editarNombreUniversidad(context: Context, id: Int, nuevoNombre: String, nuevoPresupuesto: Int, nuevaPrivacidad: Boolean) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COL_UNI_NOMBRE, nuevoNombre)
            put(DBHelper.COL_UNI_PRESUPUESTO, nuevoPresupuesto)
            put(DBHelper.COL_UNI_ESPRIVADA, if (nuevaPrivacidad) 1 else 0)
        }
        db.update(
            DBHelper.TABLE_UNIVERSIDADES,
            values,
            "${DBHelper.COL_UNI_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun editarCarrera(context: Context, id: Int, nuevoNombre: String, nuevoNroEstudiantes: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COL_CARR_NOMBRE, nuevoNombre)
            put(DBHelper.COL_CARR_NRO_ESTUDIANTES, nuevoNroEstudiantes)
        }
        db.update(
            DBHelper.TABLE_CARRERAS,
            values,
            "${DBHelper.COL_CARR_ID} = ?",
            arrayOf(id.toString())
        )
    }
}
