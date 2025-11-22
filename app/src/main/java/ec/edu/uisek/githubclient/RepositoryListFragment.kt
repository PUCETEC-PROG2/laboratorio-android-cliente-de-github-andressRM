package ec.edu.uisek.githubclient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryListFragment : Fragment() {

    // Variable para el RecyclerView
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repository_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Configuración inicial del RecyclerView (Vacío por ahora)
        recyclerView = view.findViewById(R.id.rvRepositories)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 2. Iniciar la carga de datos
        cargarRepositoriosDeInternet()
    }

    /**
     * MÉTODO: cargarRepositoriosDeInternet
     * ------------------------------------
     * EXPLICACIÓN FASE 4:
     * Aquí configuramos Retrofit y hacemos la llamada asíncrona.
     */
    private fun cargarRepositoriosDeInternet() {
        // PASO A: Crear la instancia de Retrofit
        // Definimos la URL base y el convertidor (GSON)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // PASO B: Crear el servicio (nuestra interfaz)
        val service = retrofit.create(GitHubApiService::class.java)

        // PASO C: Hacemos la llamada (Request)
        // Cambia "andressRM" por el usuario que quieras consultar (ej: "google", "square")
        val llamada = service.listRepositories("andressRM")

        // PASO D: Encolar la llamada (Asíncrono - Segundo Plano)
        llamada.enqueue(object : Callback<List<Repository>> {

            // Si hay respuesta del servidor (sea buena o error 404, 500...)
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    // ¡ÉXITO! Recibimos los datos (HTTP 200)
                    val listaRepositorios = response.body() ?: emptyList()

                    // Asignamos los datos al Adapter y lo conectamos al RecyclerView
                    val adapter = RepositoryAdapter(listaRepositorios)
                    recyclerView.adapter = adapter

                    Toast.makeText(context, "Datos cargados: ${listaRepositorios.size}", Toast.LENGTH_SHORT).show()
                } else {
                    // ERROR (Ej: Usuario no encontrado)
                    Toast.makeText(context, "Error en la respuesta: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            // Si falla la conexión (Ej: Sin internet)
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("Retrofit", "Error de conexión: ${t.message}")
                Toast.makeText(context, "Fallo la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}