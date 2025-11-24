package ec.edu.uisek.githubclient

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    // USUARIO DE GITHUB (Necesario para DELETE/PATCH)
    private val miUsuario = "andressRM"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repository_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvRepositories)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Botón Flotante (+)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabCreateRepo)
        fab.setOnClickListener {
            // Navegación a MODO CREACIÓN
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RepositoryFormFragment())
                .addToBackStack(null)
                .commit()
        }

        // Cargar datos
        cargarRepositoriosDeInternet()
    }

    private fun cargarRepositoriosDeInternet() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GitHubApiService::class.java)
        val llamada = service.listRepositories(miUsuario)

        llamada.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(call: Call<List<Repository>>, response: Response<List<Repository>>) {
                if (response.isSuccessful) {
                    val listaRepositorios = response.body() ?: emptyList()

                    // AQUÍ CONECTAMOS LOS BOTONES DEL ADAPTER
                    val adapter = RepositoryAdapter(
                        listaRepositorios,
                        onEditClick = { repo ->
                            // ✅ LÓGICA DE EDICIÓN FINAL: Enviamos el objeto Repository
                            val editFragment = RepositoryFormFragment.newInstance(repo)
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, editFragment)
                                .addToBackStack(null)
                                .commit()
                        },
                        onDeleteClick = { repo ->
                            // Lógica de borrado (DELETE)
                            mostrarDialogoConfirmacion(repo)
                        }
                    )
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(context, "Error al cargar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("API", "Error: ${t.message}")
                Toast.makeText(context, "Fallo conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Ventana emergente para confirmar si se quier borrar
    private fun mostrarDialogoConfirmacion(repo: Repository) {
        AlertDialog.Builder(context)
            .setTitle("Eliminar Repositorio")
            .setMessage("¿Estás seguro de borrar '${repo.name}'? Esto no se puede deshacer.")
            .setPositiveButton("Eliminar") { _, _ ->
                eliminarRepositorioDeGitHub(repo.name)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    // LÓGICA DE ELIMINACIÓN
    private fun eliminarRepositorioDeGitHub(nombreRepo: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GitHubApiService::class.java)

        // Llamada DELETE: repos/{dueño}/{nombre}
        service.deleteRepository(miUsuario, nombreRepo).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) { // HTTP 204 No Content
                    Toast.makeText(context, "Repositorio eliminado", Toast.LENGTH_SHORT).show()
                    cargarRepositoriosDeInternet()
                } else {
                    Toast.makeText(context, "Error al borrar: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Fallo de red al borrar", Toast.LENGTH_SHORT).show()
            }
        })
    }
}