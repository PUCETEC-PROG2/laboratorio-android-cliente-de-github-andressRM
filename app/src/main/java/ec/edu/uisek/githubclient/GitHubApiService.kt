package ec.edu.uisek.githubclient

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * INTERFAZ LAB 5
 * con método POST para crear repositorios.
 */
interface GitHubApiService {

    //
    @GET("users/{username}/repos")
    fun listRepositories(@Path("username") username: String): Call<List<Repository>>

    // Part 2: Crear Nueva para Lab 5
    // Usamos @Headers para enviar token
    @Headers(" Token ")
    @POST("user/repos")
    fun createRepository(@Body repo: Repository): Call<Repository>
}