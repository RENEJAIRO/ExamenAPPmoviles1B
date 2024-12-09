import java.time.LocalDate

data class Universidad(
    val id: Int,
    var nombre: String,
    var ubicacion: String,
    var fundacion: LocalDate,
    var publica: Boolean,
    val carreras: MutableList<Carrera> = mutableListOf()
)
