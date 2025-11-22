package ec.edu.uisek.githubclient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * INTERFAZ: GitHubApiService

 * EXPLICACIÓN FASE 4 (Retrofit):
 * Esta interfaz es el Contrato" que define cómo nos comunicamos con GitHub.
 * Retrofit leerá este archivo y generará el código de red automáticamente.
 */
interface GitHubApiService {

    // @GET: Indica que vamos a LEER datos (petición HTTP GET).
    // "users/{username}/repos": Es la parte final de la URL (Endpoint).
    // {username} es una variable que cambiara dinámicamente.
    @GET("users/{username}/repos")
    fun listRepositories(@Path("username") username: String): Call<List<Repository>>
}