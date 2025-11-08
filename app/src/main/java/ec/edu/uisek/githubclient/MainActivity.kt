package ec.edu.uisek.githubclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * CLASE: MainActivity
 * -------------------
 * EXP
 * Es el punto de entrada de la aplicación.
 * Su única responsabilidad en este Lab 3 es cargar el primer Fragmento (la Lista).
 * Gestiona las transacciones de fragmentos usando el 'SupportFragmentManager'.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Verificamos si es la primera
        if (savedInstanceState == null) {
            // TRANSACCIÓN DE FRAGMENTO
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainer, RepositoryListFragment()) // Cargamos la Lista por defecto
                .commit()
        }
    }
}