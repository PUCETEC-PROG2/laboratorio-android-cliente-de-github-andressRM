package ec.edu.uisek.githubclient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryFormFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Vincular los controles visuales Inputs y Botón
        val etName = view.findViewById<TextInputEditText>(R.id.etRepoName)
        val etDesc = view.findViewById<TextInputEditText>(R.id.etRepoDesc)
        val btnCreate = view.findViewById<Button>(R.id.btnCreate)

        // 2. Programar el clic del botón
        btnCreate.setOnClickListener {
            val nombre = etName.text.toString()
            val descripcion = etDesc.text.toString()

            // Validación simple: El nombre es obligatorio en GitHub
            if (nombre.isNotEmpty()) {
                crearRepositorioEnGitHub(nombre, descripcion)
            } else {
                etName.error = "El nombre es obligatorio"
            }
        }
    }

    // LÓGICA DE RETROFIT POST
    private fun crearRepositorioEnGitHub(nombre: String, descripcion: String) {
        // A. Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GitHubApiService::class.java)

        // B. Preparar el objeto a enviar
        // GitHub asigna las estrellas y el lenguaje automáticamente, así que envi defaults
        val nuevoRepo = Repository(
            name = nombre,
            description = descripcion,
            language = "Kotlin",
            stars = 0
        )

        // C. Ejecutar la llamada POST
        service.createRepository(nuevoRepo).enqueue(object : Callback<Repository> {
            override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                if (response.isSuccessful) {
                    // ¡ÉXITO! (HTTP 201)
                    Toast.makeText(context, "¡Repositorio Creado con Éxito!", Toast.LENGTH_LONG).show()

                    // Volver a la lista automáticamente
                    parentFragmentManager.popBackStack()
                } else {
                    // ERROR si el nombre ya existe
                    Toast.makeText(context, "Error al crear: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Repository>, t: Throwable) {
                Log.e("API", "Fallo de red: ${t.message}")
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}