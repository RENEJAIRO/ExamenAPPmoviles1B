import java.time.LocalDate
import java.util.Scanner

fun main() {
    val crud = UniversidadCRUD()
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\n=== CRUD UNIVERSIDAD ===")
        println("1. Crear Universidad")
        println("2. Listar Universidades")
        println("3. Actualizar Universidad")
        println("4. Eliminar Universidad")
        println("5. Agregar Carrera a una Universidad")
        println("6. Listar Carreras de una Universidad")
        println("7. Eliminar Carrera de una Universidad")
        println("0. Salir")
        print("Selecciona una opción: ")

        when (scanner.nextLine().toInt()) {
            1 -> {
                // Crear Universidad
                print("Ingrese ID: ")
                val id = scanner.nextLine().toInt()
                print("Ingrese nombre: ")
                val nombre = scanner.nextLine()
                print("Ingrese ubicación: ")
                val ubicacion = scanner.nextLine()
                print("Ingrese fecha de fundación (YYYY-MM-DD): ")
                val fundacion = LocalDate.parse(scanner.nextLine())
                print("¿Es pública? (true/false): ")
                val publica = scanner.nextLine().toBoolean()

                crud.crearUniversidad(Universidad(id, nombre, ubicacion, fundacion, publica))
                println("Universidad creada exitosamente.")
            }
            2 -> {
                // Listar Universidades
                val universidades = crud.listarUniversidades()
                if (universidades.isEmpty()) {
                    println("No hay universidades registradas.")
                } else {
                    universidades.forEach { println(it) }
                }
            }
            3 -> {
                // Actualizar Universidad
                print("Ingrese ID de la universidad a actualizar: ")
                val id = scanner.nextLine().toInt()
                print("Ingrese nuevo nombre: ")
                val nombre = scanner.nextLine()
                print("Ingrese nueva ubicación: ")
                val ubicacion = scanner.nextLine()
                print("Ingrese nueva fecha de fundación (YYYY-MM-DD): ")
                val fundacion = LocalDate.parse(scanner.nextLine())
                print("¿Es pública? (true/false): ")
                val publica = scanner.nextLine().toBoolean()

                crud.actualizarUniversidad(id, Universidad(id, nombre, ubicacion, fundacion, publica))
            }
            4 -> {
                // Eliminar Universidad
                print("Ingrese ID de la universidad a eliminar: ")
                val id = scanner.nextLine().toInt()
                crud.eliminarUniversidad(id)
                println("Universidad eliminada.")
            }
            5 -> {
                // Agregar Carrera a una Universidad
                print("Ingrese ID de la universidad: ")
                val universidadId = scanner.nextLine().toInt()
                print("Ingrese ID de la carrera: ")
                val id = scanner.nextLine().toInt()
                print("Ingrese nombre de la carrera: ")
                val nombre = scanner.nextLine()
                print("Ingrese duración en semestres: ")
                val duracionSemestres = scanner.nextLine().toInt()
                print("Ingrese costo semestral: ")
                val costoSemestral = scanner.nextLine().toDouble()
                print("¿Está acreditada? (true/false): ")
                val acreditada = scanner.nextLine().toBoolean()

                crud.agregarCarrera(universidadId, Carrera(id, nombre, duracionSemestres, costoSemestral, acreditada))
                println("Carrera agregada exitosamente.")
            }
            6 -> {
                // Listar Carreras de una Universidad
                print("Ingrese ID de la universidad: ")
                val universidadId = scanner.nextLine().toInt()
                val carreras = crud.listarCarreras(universidadId)
                if (carreras.isEmpty()) {
                    println("No hay carreras registradas en esta universidad.")
                } else {
                    carreras.forEach { println(it) }
                }
            }
            7 -> {
                // Eliminar Carrera de una Universidad
                print("Ingrese ID de la universidad: ")
                val universidadId = scanner.nextLine().toInt()
                print("Ingrese ID de la carrera a eliminar: ")
                val carreraId = scanner.nextLine().toInt()
                crud.eliminarCarrera(universidadId, carreraId)
                println("Carrera eliminada.")
            }
            0 -> {
                // Salir
                println("Saliendo del programa.")
                break
            }
            else -> println("Opción inválida, intente nuevamente.")
        }
    }
}
