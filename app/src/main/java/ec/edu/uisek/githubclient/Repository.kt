package ec.edu.uisek.githubclient // <---  paquete real

/**
 * @param name: El título del repositorio.
 * @param description: Breve resumen de qué hace el proyecto.
 * @param language: El lenguaje de programación principal (ej. Kotlin).
 * @param stars: Número ficticio de estrellas para mostrar popularidad.
 */
data class Repository(
    val name: String,
    val description: String,
    val language: String,
    val stars: Int
)