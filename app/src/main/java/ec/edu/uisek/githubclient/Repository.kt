package ec.edu.uisek.githubclient

import android.os.Parcelable // Importación requerida
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize // Importación requerida

/**
 *  Repository

 * @Parcelize y : Parcelable le dan el sello necesario para que el objeto
 * pueda vajar dentro del Bundle entre los Fragmentos.
 */
@Parcelize
data class Repository(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("language")
    val language: String?,

    @SerializedName("stargazers_count")
    val stars: Int
) : Parcelable //