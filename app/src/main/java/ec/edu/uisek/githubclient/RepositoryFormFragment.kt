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

    private var editingRepo: Repository? = null
    private val miUsuario = "andressRM" // Tu usuario (necesario para la edición)

    companion object {
        const val ARG_REPO = "repo_data"

        // Función estática para pasar el objeto Repository al Fragmento
        fun newInstance(repo: Repository): RepositoryFormFragment {
            val fragment = RepositoryFormFragment()
            val args = Bundle()
            // El objeto Repository debe ser Parcelable (asumo que lo es)
            args.putParcelable(ARG_REPO, repo)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<TextInputEditText>(R.id.etRepoName)
        val etDesc = view.findViewById<TextInputEditText>(R.id.etRepoDesc)
        val btnCreate = view.findViewById<Button>(R.id.btnCreate)

        // Revisar si recibimos datos (Modo Edición)
        arguments?.let {
            // Se asume que Repository es Parcelable
            editingRepo = it.getParcelable(ARG_REPO)
            setupEditMode(etName, etDesc, btnCreate)
        }

        // Si es Modo CREACIÓN (Lógica Lab 5)
        if (editingRepo == null) {
            btnCreate.setOnClickListener {
                val nombre = etName.text.toString()
                val descripcion = etDesc.text.toString()
                if (nombre.isNotEmpty()) {
                    crearRepositorioEnGitHub(nombre, descripcion)
                } else {
                    etName.error = "El nombre es obligatorio"
                }
            }
        }
    }

    // LÓGICA DE INTERFAZ para Modo Edición (REQUISITO EXAMEN)
    private fun setupEditMode(etName: TextInputEditText, etDesc: TextInputEditText, btnCreate: Button) {
        // REQUISITO: El nombre NO debe ser editable
        etName.setText(editingRepo?.name)
        etName.isEnabled = false

        btnCreate.text = "Guardar Cambios"
        etDesc.setText(editingRepo?.description)

        // Lógica de Edición (PATCH)
        btnCreate.setOnClickListener {
            val nuevaDescripcion = etDesc.text.toString()
            if (editingRepo != null) {
                editarRepositorioEnGitHub(editingRepo!!, nuevaDescripcion)
            }
        }
    }

    // LÓGICA DE API: CREAR (POST - Lab 5)
    private fun crearRepositorioEnGitHub(nombre: String, descripcion: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(GitHubApiService::class.java)

        val nuevoRepo = Repository(
            name = nombre,
            description = descripcion,
            language = "Kotlin",
            stars = 0
        )

        service.createRepository(nuevoRepo).enqueue(object : Callback<Repository> {
            override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "¡Repositorio creado!", Toast.LENGTH_LONG).show()
                    parentFragmentManager.popBackStack()
                } else {
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Repository>, t: Throwable) {
                Log.e("API", "Error POST: ${t.message}")
                Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // LÓGICA DE API: EDITAR (PATCH - EXAMEN)
    private fun editarRepositorioEnGitHub(repo: Repository, nuevaDescripcion: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(GitHubApiService::class.java)

        // Solo enviamos los campos que queremos actualizar
        val actualizacion = Repository(
            name = repo.name,
            description = nuevaDescripcion,
            language = repo.language,
            stars = repo.stars
        )

        service.editRepository(miUsuario, repo.name, actualizacion).enqueue(object : Callback<Repository> {
            override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "¡Edición exitosa!", Toast.LENGTH_LONG).show()
                    parentFragmentManager.popBackStack()
                } else {
                    Toast.makeText(context, "Error ${response.code()} al editar", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Repository>, t: Throwable) {
                Toast.makeText(context, "Fallo de red al editar", Toast.LENGTH_SHORT).show()
            }
        })
    }
}