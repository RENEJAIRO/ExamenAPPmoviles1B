import java.io.File
import java.time.LocalDate


// Clase CRUD
class UniversidadCRUD {
    private val universidades = mutableListOf<Universidad>()
    private val archivo = File("universidades.txt")

    init {
        cargarDatosDesdeArchivo()
    }

    fun crearUniversidad(universidad: Universidad) {
        universidades.add(universidad)
        guardarDatosEnArchivo()
    }

    fun listarUniversidades(): List<Universidad> {
        return universidades
    }

    fun actualizarUniversidad(id: Int, nuevaUniversidad: Universidad) {
        val index = universidades.indexOfFirst { it.id == id }
        if (index != -1) {
            universidades[index] = nuevaUniversidad
            guardarDatosEnArchivo()
        } else {
            println("Universidad no encontrada.")
        }
    }

    fun eliminarUniversidad(id: Int) {
        val index = universidades.indexOfFirst { it.id == id }
        if (index != -1) {
            universidades.removeAt(index)
            guardarDatosEnArchivo()
        } else {
            println("Universidad no encontrada.")
        }
    }

    fun agregarCarrera(universidadId: Int, carrera: Carrera) {
        val universidad = universidades.find { it.id == universidadId }
        if (universidad != null) {
            universidad.carreras.add(carrera)
            guardarDatosEnArchivo()
        } else {
            println("Universidad no encontrada.")
        }
    }

    fun listarCarreras(universidadId: Int): List<Carrera> {
        return universidades.find { it.id == universidadId }?.carreras ?: emptyList()
    }

    fun eliminarCarrera(universidadId: Int, carreraId: Int) {
        val universidad = universidades.find { it.id == universidadId }
        if (universidad != null) {
            val index = universidad.carreras.indexOfFirst { it.id == carreraId }
            if (index != -1) {
                universidad.carreras.removeAt(index)
                guardarDatosEnArchivo()
            } else {
                println("Carrera no encontrada.")
            }
        } else {
            println("Universidad no encontrada.")
        }
    }

    private fun guardarDatosEnArchivo() {
        archivo.writeText(universidades.joinToString("\n") { universidad ->
            val carrerasString = universidad.carreras.joinToString(";") { carrera ->
                "${carrera.id},${carrera.nombre},${carrera.duracionSemestres},${carrera.costoSemestral},${carrera.acreditada}"
            }
            "${universidad.id},${universidad.nombre},${universidad.ubicacion},${universidad.fundacion},${universidad.publica}|$carrerasString"
        })
    }

    private fun cargarDatosDesdeArchivo() {
        if (archivo.exists()) {
            val lineas = archivo.readLines()
            lineas.forEach { linea ->
                try {
                    val partes = linea.split("|")
                    if (partes.isEmpty() || partes[0].isBlank()) throw IllegalArgumentException("Línea vacía o mal formada")

                    // Datos de la universidad
                    val universidadDatos = partes[0].split(",")
                    if (universidadDatos.size < 5 || universidadDatos.any { it.isBlank() }) {
                        throw IllegalArgumentException("Datos insuficientes o inválidos para la universidad")
                    }

                    val universidad = Universidad(
                        id = universidadDatos[0].toInt(),
                        nombre = universidadDatos[1],
                        ubicacion = universidadDatos[2],
                        fundacion = LocalDate.parse(universidadDatos[3]),
                        publica = universidadDatos[4].toBoolean()
                    )

                    // Datos de las carreras (si existen)
                    if (partes.size > 1 && partes[1].isNotBlank()) {
                        val carrerasDatos = partes[1].split(";")
                        carrerasDatos.forEach { carreraString ->
                            val carreraPartes = carreraString.split(",")
                            if (carreraPartes.size < 5 || carreraPartes.any { it.isBlank() }) {
                                throw IllegalArgumentException("Datos insuficientes o inválidos para la carrera")
                            }

                            val carrera = Carrera(
                                id = carreraPartes[0].toInt(),
                                nombre = carreraPartes[1],
                                duracionSemestres = carreraPartes[2].toInt(),
                                costoSemestral = carreraPartes[3].toDouble(),
                                acreditada = carreraPartes[4].toBoolean()
                            )
                            universidad.carreras.add(carrera)
                        }
                    }

                    universidades.add(universidad)
                } catch (e: Exception) {
                    println("Error al procesar la línea: '$linea'. Detalles: ${e.message}")
                }
            }
        }
    }
}
