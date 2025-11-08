package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * FRAGMENTO: RepositoryListFragment
 * --------------------------------
 * EXP
 * Este Fragment representa la pantalla de "Lista de Repositorios".
 * Su responsabilidad es:
 * 1. Crear los datos de prueba (Hardcoded).
 * 2. Configurar el RecyclerView.
 * 3. Conectar el Adapter con la lista.
 */
class RepositoryListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout que contiene el RecyclerView vacío
        return inflater.inflate(R.layout.fragment_repository_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Buscamos el RecyclerView en el diseño
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvRepositories)

        // 2. Definimos que la lista será vertical (LayoutManager)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 3. DATOS HARDCODED (Requisito del Lab)
        // Creamos una lista manual de repositorios para probar la UI
        val misRepositorios = listOf(
            Repository("Proyecto 1 ", "App de prueba en Android", "Kotlin", 15),
            Repository("Calculadora", "Calculadora básica", "Java", 8),
            Repository("Lista", "Lista de tareas", "Kotlin", 42),
            Repository("Retrofit Client", "Consumo de APIs REST", "Kotlin", 120),
            Repository("Game", "Motor de juegos 2D", "C++", 300)
        )

        // 4. Conectamos el Adapter
        // Le pasamos la lista de datos al adaptador y se lo asignamos al RecyclerView
        val adapter = RepositoryAdapter(misRepositorios)
        recyclerView.adapter = adapter
    }
}