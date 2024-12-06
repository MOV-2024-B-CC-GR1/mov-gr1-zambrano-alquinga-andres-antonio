package org.example

import java.util.*

fun main() {
    println("Hello World!")

    val inmutable: String = "azambrano";
    //inmutable = "ccordova";

    var mutable: String = "dandrade";
    mutable = "gtarapues";

    var mutable1: String = "hsanchez";
    mutable1 = "azambrano"


    val strEjemplo = "AppMoviles"
    val intEjemplo: Int = 14;

    val fechaNacimiento: Date = Date()
    val estadoCivilWhen: String = "C"

    when (estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" ->{
            println("Soltero")
        }
        else ->{
            println("Desconocido")
        }
    }

    val esSoltero: Boolean = true
    val coqueteo = if(esSoltero) "Si" else "No"



    imprimirNombre("Andres Zambrano")




    val suma = Suma(2, 3)
    println("Valor de pi: ${Suma.pi}")
    println("Cuadrado de 2: ${Suma.elevarAlCuadrado(2)}")
    println("Suma de 2 + 3: ${suma.sumar(2, 3)}")
    println("Historial de sumas: ${suma.mostrarHistorial()}")




    val numeros = arrayOf(1, 2, 3, 4, 5)
    numeros[2] = 10 // Modificamos el tercer elemento
    println("Contenido del arreglo: ${numeros.joinToString(", ")}")

    //====================

    //val numeros = arrayOf(1, 2, 3, 4, 5)

    // Uso de foreach
    println("Usando foreach:")
    numeros.forEach { println(it) }

    // Uso de map para multiplicar cada número por 2
    val numerosDuplicados = numeros.map { it * 2 }
    println("Números duplicados: ${numerosDuplicados.joinToString(", ")}")

    //=================
    //val numeros = arrayOf(1, 2, 3, 4, 5)

    val hayPares = numeros.any { it % 2 == 0 }
    println("¿Hay números pares? $hayPares")

    val todosPositivos = numeros.all { it > 0 }
    println("¿Todos los números son positivos? $todosPositivos")

    //===================

    //val numeros = arrayOf(1, 2, 3, 4, 5)

    val sumaTotal = numeros.reduce { acumulador, valor -> acumulador + valor }
    println("Suma total usando reduce: $sumaTotal")

    val productoTotal = numeros.reduce { acumulador, valor -> acumulador * valor }
    println("Producto total usando reduce: $productoTotal")

    //======================

    val numeros = listOf(1, 2, 3, 4, 5)

    // Reducir lista para concatenar valores como texto
    // Convertir los valores a String antes de reducirlos
    val concatenacion = numeros.map { it.toString() }
        .reduce { acumulador, valor -> "$acumulador-$valor" }
    println("Concatenación de valores: $concatenacion")


}

fun imprimirNombre(nombre:String): Unit{
    fun otraFuncionAdentro(){
        println("Hola que haciendo")
    }

    println("Nombre: $nombre")
    println("Nombre: ${nombre}")

}



open class ClaseBase {
    protected var propiedadProtegida: Int = 0
}

class ClaseDerivada : ClaseBase() {
    fun mostrarPropiedad() {
        println("Propiedad protegida: $propiedadProtegida")
    }
}

open class Numeros(val numero1: Int, val numero2: Int) {
    init {
        println("Inicializando con $numero1 y $numero2")
    }
}


class Suma(numero1: Int, numero2: Int) {
    companion object {
        const val pi = 3.14
        fun elevarAlCuadrado(valor: Int) = valor * valor
    }

    private val historial = mutableListOf<Int>()

    fun sumar(a: Int, b: Int): Int {
        val resultado = a + b
        historial.add(resultado)
        return resultado
    }

    fun mostrarHistorial() = historial.toString()
}
