package com.example.deber02_2b_aaza

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "empresa.db"
        const val DATABASE_VERSION = 1

        const val TABLE_UNIVERSIDADES = "universidades"
        const val TABLE_CARRERAS = "carreras"

        const val COL_UNI_ID = "id"
        const val COL_UNI_NOMBRE = "nombre"
        const val COL_UNI_PRESUPUESTO = "presupuesto_anual"
        const val COL_UNI_ESPRIVADA = "es_privada"

        const val COL_CARR_ID = "id"
        const val COL_CARR_NOMBRE = "nombre"
        const val COL_CARR_NRO_ESTUDIANTES = "nro_estudiantes"
        const val COL_CARR_UNI_ID = "uni_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUniversidades = """
            CREATE TABLE $TABLE_UNIVERSIDADES (
                $COL_UNI_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_UNI_NOMBRE TEXT NOT NULL,
                $COL_UNI_PRESUPUESTO INTEGER NOT NULL,
                $COL_UNI_ESPRIVADA INTEGER NOT NULL CHECK ($COL_UNI_ESPRIVADA IN (0,1))
            )
        """.trimIndent()

        val createCarreras = """
            CREATE TABLE $TABLE_CARRERAS (
                $COL_CARR_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_CARR_NOMBRE TEXT NOT NULL,
                $COL_CARR_NRO_ESTUDIANTES INTEGER NOT NULL,
                $COL_CARR_UNI_ID INTEGER NOT NULL,
                FOREIGN KEY($COL_CARR_UNI_ID) REFERENCES $TABLE_UNIVERSIDADES($COL_UNI_ID) ON DELETE CASCADE
            )
        """.trimIndent()

        db.execSQL(createUniversidades)
        db.execSQL(createCarreras)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CARRERAS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_UNIVERSIDADES")
        onCreate(db)
    }
}