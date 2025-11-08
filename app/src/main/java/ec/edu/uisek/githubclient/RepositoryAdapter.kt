package ec.edu.uisek.githubclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * CLASE: RepositoryAdapter

 * EXP:
 * Este es el motor del RecyclerView. Su trabajo es tomar la lista de datos (objetos Repository)
 * y convertirlos en vistas visuales que el usuario puede ver en pantalla.
 * Actúa como un puente entre la lógica (Kotlin) y la interfaz (XML).
 */
class RepositoryAdapter(private val repositoryList: List<Repository>) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    /**
     * CLASE INTERNA: ViewHolder
     * EXP
     * El ViewHolder es como una "cajita" que guarda las referencias a los elementos visuales
     * (el icono, el título, la descripción) para no tener que buscarlos una y otra vez.
     * Optimiza el rendimiento de la lista.
     */
    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.tvRepoName)
        val repoDesc: TextView = itemView.findViewById(R.id.tvRepoDesc)
        val language: TextView = itemView.findViewById(R.id.tvLanguage)
        val icon: ImageView = itemView.findViewById(R.id.imgIcon)
    }

    /**
     * MÉTODO: onCreateViewHolder

     * Este método se ejecuta cuando el RecyclerView necesita crear una nueva fila.
     * Aquí "inflamos" el diseño XML (item_repository) que creamos en el paso anterior.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    /**
     * MÉTODO: onBindViewHolder

      Este método toma los datos de una posición específica de la lista
     * y los asigna a los controles visuales del ViewHolder.
     * Es decir: Pone el texto correcto en el TextView correcto.
     */
    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val currentRepo = repositoryList[position]

        // Asignamos los datos a la vista
        holder.repoName.text = currentRepo.name
        holder.repoDesc.text = currentRepo.description
        holder.language.text = currentRepo.language

        // Por ahora usamos un icono genérico, pero aquí cargaríamos la imagen real
        holder.icon.setImageResource(android.R.drawable.sym_def_app_icon)
    }

    /**
     * MÉTODO: getItemCount

     * Le dice al RecyclerView cuántos elementos tiene la lista en total.
     */
    override fun getItemCount(): Int {
        return repositoryList.size
    }
}