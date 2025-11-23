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
import com.google.android.material.floatingactionbutton.FloatingActionButton // IMPORTANTE: Importamos el botón
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repository_list, container, false)
    }

    //  AQUÍ ESTÁ EL MÉTODO Oview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Configuración del RecyclerView
        recyclerView = view.findViewById(R.id.rvRepositories)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 2. Lógica del Botón Flotante (+)
        // Buscar el botón por su ID xml
        val fab = view.findViewById<FloatingActionButton>(R.id.fabCreateRepo)

        fab.setOnClickListener {
            // Al hacer clic, navegamos al formulario
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RepositoryFormFragment())
                .addToBackStack(null) // Para volver atrás
                .commit()
        }

        // 3. Iniciar la carga de datos (Retrofit)
        cargarRepositoriosDeInternet()
    }

    // ... (El resto del código de cargarRepositoriosDeInternet sigue igual) ...
    private fun cargarRepositoriosDeInternet() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GitHubApiService::class.java)
        val llamada = service.listRepositories("andressRM")

        llamada.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    val listaRepositorios = response.body() ?: emptyList()
                    val adapter = RepositoryAdapter(listaRepositorios)
                    recyclerView.adapter = adapter
                    Toast.makeText(context, "Datos cargados: ${listaRepositorios.size}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("Retrofit", "Error: ${t.message}")
                Toast.makeText(context, "Fallo conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}