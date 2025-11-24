package ec.edu.uisek.githubclient

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface GitHubApiService {

    // 1. LEER (GET)
    @GET("users/{username}/repos")
    fun listRepositories(@Path("username") username: String): Call<List<Repository>>

    // 2. CREAR POST
    @Headers("Authorization: token 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")
    @POST("user/repos")
    fun createRepository(@Body repo: Repository): Call<Repository>

    // 3. ELIMINAR DELETE
    @Headers("Authorization: token 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")
    @DELETE("repos/{owner}/{repo}")
    fun deleteRepository(
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ): Call<Void>

    // 4. EDITAR PATCH
    @Headers("Authorization: token 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")
    @PATCH("repos/{owner}/{repo}")
    fun editRepository(
        @Path("owner") owner: String,
        @Path("repo") repoName: String,
        @Body repoUpdate: Repository
    ): Call<Repository>
}