package app.apexsoftware.omadaassessment.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickrResponse(
    val photos: Photos,
    val stat: String
)

@Serializable
data class Photos(
    val page: Int,
    val pages: Int,
    @SerialName("perpage") val perPage: Int,
    val total: Int,
    val photo: List<Photo>
)

@Parcelize
@Serializable
data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    @SerialName("ispublic") val isPublic: Int,
    @SerialName("isfriend") val isFriend: Int,
    @SerialName("isfamily") val isFamily: Int,
): Parcelable