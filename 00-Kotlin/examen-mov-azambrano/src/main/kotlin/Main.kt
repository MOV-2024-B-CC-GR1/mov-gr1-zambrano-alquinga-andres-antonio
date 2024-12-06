package main

import models.Carrera
import models.Universidad
import services.UniversidadService
import kotlin.system.exitProcess

fun main() {
    val universidadService = UniversidadService()

    while (true) {
        println("\n--- MENÚ PRINCIPAL ---")
        println("1. CRUD Universidades")
        println("2. CRUD Carreras dentro de una Universidad")
        println("3. Salir")

        print("Selecciona una opción: ")
        when (readLine()?.toIntOrNull()) {
            1 -> menuUniversidades(universidadService)
            2 -> menuCarreras(universidadService)
            3 -> {
                println("Saliendo del programa.")
                exitProcess(0)
            }
            else -> println("Opción no válida. Intenta nuevamente.")
        }
    }
}

fun menuUniversidades(service: UniversidadService) {
    while (true) {
        println("\n--- MENÚ UNIVERSIDADES ---")
        println("1. Crear Universidad")
        println("2. Leer Universidades")
        println("3. Actualizar Universidad")
        println("4. Eliminar Universidad")
        println("5. Volver al Menú Principal")

        println("\n")
        print("Selecciona una opción: ")
        when (readLine()?.toIntOrNull()) {
            1 -> crearUniversidad(service)
            2 -> leerUniversidades(service)
            3 -> actualizarUniversidad(service)
            4 -> eliminarUniversidad(service)
            5 -> return
            else -> println("Opción no válida. Intenta nuevamente.")
        }
    }
}

fun crearUniversidad(service: UniversidadService) {
    println("\n")
    println("===== INGRESE LOS DATOS DE LA UNIVERSIDAD =====")
    println("\n")
    print("Nombre de la universidad: ")
    val nombre = readLine() ?: ""
    print("Ubicación: ")
    val ubicacion = readLine() ?: ""
    print("Año de fundación: ")
    val fundacion = readLine()?.toIntOrNull() ?: 0
    print("¿Es pública? (true/false): ")
    val esPublica = readLine()?.toBoolean() ?: false
    print("Presupuesto anual: ")
    val presupuesto = readLine()?.toDoubleOrNull() ?: 0.0

    val universidad = Universidad(nombre, ubicacion, fundacion, esPublica, presupuesto)
    service.crearUniversidad(universidad)
    println("Universidad creada exitosamente.")
}

fun leerUniversidades(service: UniversidadService) {
    val universidades = service.leerUniversidades()
    if (universidades.isEmpty()) {
        println("No hay universidades registradas.")
    } else {
        universidades.forEachIndexed { index, universidad ->
            println("\n")
            println("Universidad ${index + 1}: ${universidad.nombre}")
            println("\tUbicación: ${universidad.ubicacion}")
            println("\tFundación: ${universidad.fundacion}")
            println("\tEs pública: ${universidad.esPublica}")
            println("\tPresupuesto: ${universidad.presupuesto}")
            println("\tCarreras (${universidad.carreras.size}):")
            universidad.carreras.forEach { carrera ->
                println("\t\t- ${carrera.nombre} (${carrera.duracionAnios} años, ${carrera.cantidadEstudiantes} estudiantes, " +
                        "Acreditada: ${carrera.acreditada}, Costo por semestre: ${carrera.costoSemestre})")
            }
        }
    }
}

fun actualizarUniversidad(service: UniversidadService) {
    leerUniversidades(service)
    println("\n")
    print("Selecciona el índice de la universidad a actualizar: ")
    val index = readLine()?.toIntOrNull() ?: return

    if (index !in service.leerUniversidades().indices) {
        println("\n")
        println("Índice inválido.")
        return
    }

    println("\n")
    println("===== INGRESE LOS NUEVOS DATOS DE LA UNIVERSIDAD =====")
    println("\n")
    print("Nuevo nombre de la universidad: ")
    val nombre = readLine() ?: ""
    print("Nueva ubicación: ")
    val ubicacion = readLine() ?: ""
    print("Nuevo año de fundación: ")
    val fundacion = readLine()?.toIntOrNull() ?: 0
    print("¿Es pública? (true/false): ")
    val esPublica = readLine()?.toBoolean() ?: false
    print("Nuevo presupuesto anual: ")
    val presupuesto = readLine()?.toDoubleOrNull() ?: 0.0

    val universidadActualizada = Universidad(nombre, ubicacion, fundacion, esPublica, presupuesto)
    service.actualizarUniversidad(index, universidadActualizada)
    println("Universidad actualizada exitosamente.")
}

fun eliminarUniversidad(service: UniversidadService) {
    leerUniversidades(service)
    println("\n")
    print("Selecciona el índice de la universidad a eliminar: ")
    val index = readLine()?.toIntOrNull() ?: return
    println("\n")
    if (index in service.leerUniversidades().indices) {
        service.eliminarUniversidad(index)
        println("\n")
        println("Universidad eliminada exitosamente.")
    } else {
        println("\n")
        println("Índice inválido.")
    }
}

fun menuCarreras(service: UniversidadService) {
    leerUniversidades(service)
    println("\n")
    print("Selecciona el índice de la universidad para gestionar sus carreras: ")
    val index = readLine()?.toIntOrNull() ?: return

    if (index !in service.leerUniversidades().indices) {
        println("Índice inválido.")
        return
    }

    val universidad = service.leerUniversidades()[index]
    while (true) {
        println("\n--- MENÚ CARRERAS ---")
        println("1. Crear Carrera")
        println("2. Leer Carreras")
        println("3. Actualizar Carrera")
        println("4. Eliminar Carrera")
        println("5. Volver al Menú de Universidades")
        println("\n")
        print("Selecciona una opción: ")
        when (readLine()?.toIntOrNull()) {
            1 -> crearCarrera(service, universidad)
            2 -> leerCarreras(universidad)
            3 -> actualizarCarrera(service, universidad)
            4 -> eliminarCarrera(service, universidad)
            5 -> return
            else -> println("Opción no válida. Intenta nuevamente.")
        }
    }
}

fun crearCarrera(service: UniversidadService, universidad: Universidad) {
    println("\n")
    println("===== INGRESE LOS DATOS DE LA CARRERA =====")
    println("\n")
    print("Nombre de la carrera: ")
    val nombre = readLine() ?: ""
    print("Duración en años: ")
    val duracionAnios = readLine()?.toIntOrNull() ?: 0
    print("Cantidad de estudiantes: ")
    val cantidadEstudiantes = readLine()?.toIntOrNull() ?: 0
    print("¿Está acreditada? (true/false): ")
    val acreditada = readLine()?.toBoolean() ?: false
    print("Costo por semestre: ")
    val costoSemestre = readLine()?.toDoubleOrNull() ?: 0.0

    val carrera = Carrera(nombre, duracionAnios, cantidadEstudiantes, acreditada, costoSemestre)
    service.agregarCarrera(universidad, carrera)
    println("Carrera añadida exitosamente a ${universidad.nombre}.")
}

fun leerCarreras(universidad: Universidad) {
    if (universidad.carreras.isEmpty()) {
        println("\n")
        println("No hay carreras registradas en ${universidad.nombre}.")
    } else {
        universidad.carreras.forEachIndexed { index, carrera ->
            println("\n")
            println("Carrera ${index + 1}: ${carrera.nombre}")
            println("\tDuración: ${carrera.duracionAnios} años")
            println("\tCantidad de estudiantes: ${carrera.cantidadEstudiantes}")
            println("\tAcreditada: ${carrera.acreditada}")
            println("\tCosto por semestre: ${carrera.costoSemestre}")
        }
    }
}

fun actualizarCarrera(service: UniversidadService, universidad: Universidad) {
    leerCarreras(universidad)
    println("\n")
    print("Selecciona el índice de la carrera a actualizar: ")
    val index = readLine()?.toIntOrNull() ?: return

    if (index !in universidad.carreras.indices) {
        println("\n")
        println("Índice inválido.")
        return
    }
    println("\n")
    println("===== INGRESE LOS NUEVOS DATOS DE LA CARRERA =====")
    println("\n")
    print("Nuevo nombre de la carrera: ")
    val nombre = readLine() ?: ""
    print("Nueva duración en años: ")
    val duracionAnios = readLine()?.toIntOrNull() ?: 0
    print("Nueva cantidad de estudiantes: ")
    val cantidadEstudiantes = readLine()?.toIntOrNull() ?: 0
    print("¿Está acreditada? (true/false): ")
    val acreditada = readLine()?.toBoolean() ?: false
    print("Nuevo costo por semestre: ")
    val costoSemestre = readLine()?.toDoubleOrNull() ?: 0.0

    val carreraActualizada = Carrera(nombre, duracionAnios, cantidadEstudiantes, acreditada, costoSemestre)
    service.actualizarCarrera(universidad, index, carreraActualizada)
    println("Carrera actualizada exitosamente.")
}

fun eliminarCarrera(service: UniversidadService, universidad: Universidad) {
    leerCarreras(universidad)
    println("\n")
    print("Selecciona el índice de la carrera a eliminar: ")
    val index = readLine()?.toIntOrNull() ?: return

    if (index in universidad.carreras.indices) {
        service.eliminarCarrera(universidad, index)
        println("\n")
        println("Carrera eliminada exitosamente.")
    } else {
        println("\n")
        println("Índice inválido.")
    }
}
