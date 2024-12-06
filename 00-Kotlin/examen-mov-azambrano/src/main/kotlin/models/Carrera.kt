package models

import java.io.Serializable

data class Carrera(
    val nombre: String,
    val duracionAnios: Int,
    val cantidadEstudiantes: Int,
    val acreditada: Boolean,
    val costoSemestre: Double
) : Serializable
