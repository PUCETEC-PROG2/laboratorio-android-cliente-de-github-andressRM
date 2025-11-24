package ec.edu.uisek.githubclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * ADAPTER ACTUALIZADO
 * Ahora recibe dos funciones para manejar los clics de Editar y Borrar.
 */
class RepositoryAdapter(
    private val repositoryList: List<Repository>,
    private val onEditClick: (Repository) -> Unit,   // Acción al tocar Lápiz
    private val onDeleteClick: (Repository) -> Unit  // Acción al tocar Basura
) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.tvRepoName)
        val repoDesc: TextView = itemView.findViewById(R.id.tvRepoDesc)
        val language: TextView = itemView.findViewById(R.id.tvLanguage)
        val icon: ImageView = itemView.findViewById(R.id.imgIcon)

        // Botones de acción
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val currentRepo = repositoryList[position]

        // Asignar datos visuales
        holder.repoName.text = currentRepo.name
        holder.repoDesc.text = currentRepo.description ?: "Sin descripción"
        holder.language.text = currentRepo.language ?: "Varios"
        holder.icon.setImageResource(android.R.drawable.sym_def_app_icon)

        //  LÓGICA DE BOTONES

        // 1. Clic en EDITAR (Lápiz
        holder.btnEdit.setOnClickListener {
            onEditClick(currentRepo) // Avisamos al Fragmento que quieren editar este repo
        }

        // 2. Clic en ELIMINAR (Basura)
        holder.btnDelete.setOnClickListener {
            onDeleteClick(currentRepo) // Avisamos al Fragmento que quieren borrar este repo
        }
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }
}