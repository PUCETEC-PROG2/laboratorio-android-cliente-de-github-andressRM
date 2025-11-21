package ec.edu.uisek.githubclient

import com.google.gson.annotations.SerializedName

/**
 * Data Class: Repository

 * EXPLICACIÓN FASE 4 (Retrofit):
 *  modelo para mapear los datos que vienen de la API de GitHub.
 *
 * @SerializedName: Es la etiqueta que le dice a GSON:
 * "Oye, busca en el JSON el campo 'stargazers_count' y guarda su valor en mi variable 'stars'".
 */
data class Repository(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String?, // El ? significa que aceptamos nulos (si no hay descripción)

    @SerializedName("language")
    val language: String?,    // Puede ser nulo si el repo no tiene lenguaje definido

    @SerializedName("stargazers_count") // renombramiento!
    val stars: Int
)