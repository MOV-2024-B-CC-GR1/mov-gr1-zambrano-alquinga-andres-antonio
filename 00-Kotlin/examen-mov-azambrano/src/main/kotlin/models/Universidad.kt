package models

import java.io.Serializable

data class Universidad(
    val nombre: String,
    val ubicacion: String,
    val fundacion: Int,
    val esPublica: Boolean,
    val presupuesto: Double,
    var carreras: MutableList<Carrera> = mutableListOf()  // Lista de carreras asociadas, cambié a var para permitir modificaciones
) : Serializable
