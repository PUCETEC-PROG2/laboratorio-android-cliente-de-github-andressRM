package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * FRAGMENTO: RepositoryFormFragment
 * --------------------------------
 * EXP:
 * Este fragmento maneja la pantalla de creación de repositorios.
 * En esta fase del laboratorio (Lab 3), su función es puramente visual (UI).
 * Muestra los campos de entrada (EditText) definidos en el XML.
 */
class RepositoryFormFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Simplemente inflamos el diseño del formulario
        return inflater.inflate(R.layout.fragment_new_repository, container, false)
    }
}