package services

import models.Universidad
import models.Carrera
import java.io.*

class UniversidadService {
    private val universidades: MutableList<Universidad> = mutableListOf()

    // Crear universidad
    fun crearUniversidad(universidad: Universidad) {
        universidades.add(universidad)
        guardarEnArchivo()
    }

    // Leer todas las universidades
    fun leerUniversidades(): List<Universidad> {
        cargarDesdeArchivo()
        return universidades
    }

    // Actualizar universidad
    fun actualizarUniversidad(index: Int, universidad: Universidad) {
        if (index in universidades.indices) {
            universidades[index] = universidad
            guardarEnArchivo()
        }
    }

    // Eliminar universidad
    fun eliminarUniversidad(index: Int) {
        if (index in universidades.indices) {
            universidades.removeAt(index)
            guardarEnArchivo()
        }
    }

    // Guardar las universidades en archivo
    private fun guardarEnArchivo() {
        try {
            ObjectOutputStream(FileOutputStream("universidades.dat")).use { oos ->
                oos.writeObject(universidades)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Cargar las universidades desde archivo
    private fun cargarDesdeArchivo() {
        try {
            ObjectInputStream(FileInputStream("universidades.dat")).use { ois ->
                val data = ois.readObject() as? MutableList<Universidad>
                if (data != null) {
                    universidades.clear()
                    universidades.addAll(data)
                }
            }
        } catch (e: FileNotFoundException) {
            println("Archivo no encontrado. Se iniciará con una lista vacía.")
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    // Añadir carrera a una universidad
    fun agregarCarrera(universidad: Universidad, carrera: Carrera) {
        universidad.carreras.add(carrera)
        guardarEnArchivo()  // Guardar la universidad después de agregar la carrera
    }

    // Actualizar carrera
    fun actualizarCarrera(universidad: Universidad, index: Int, carrera: Carrera) {
        if (index in universidad.carreras.indices) {
            universidad.carreras[index] = carrera
            guardarEnArchivo()
        }
    }

    // Eliminar carrera
    fun eliminarCarrera(universidad: Universidad, index: Int) {
        if (index in universidad.carreras.indices) {
            universidad.carreras.removeAt(index)
            guardarEnArchivo()
        }
    }
}
