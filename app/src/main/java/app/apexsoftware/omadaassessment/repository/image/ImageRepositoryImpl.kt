package app.apexsoftware.omadaassessment.repository.image

import app.apexsoftware.omadaassessment.BuildConfig
import app.apexsoftware.omadaassessment.data.FlickrResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import javax.inject.Inject


class ImageRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : ImageRepository {

    override suspend fun getRecentImages(
        extras: String?,
        perPage: Int?,
        page: Int?
    ): FlickrResponse {
        return httpClient.get(BuildConfig.FLIKR_URL) {
            url {
                parameters.append("method", "flickr.photos.getRecent")
                parameters.append("format", "json")
                parameters.append("nojsoncallback", "1")
                parameters.append("api_key", BuildConfig.FLIKR_API_KEY)
                if (extras != null) parameters.append("extras", extras)
                parameters.append("per_page", perPage.toString())
                if (page != null) parameters.append("page", page.toString())
            }
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun searchImages(query: String): FlickrResponse {
        return httpClient.get(BuildConfig.FLIKR_URL) {
            url {
                parameters.append("method", "flickr.photos.search")
                parameters.append("api_key", BuildConfig.FLIKR_API_KEY)
                parameters.append("text", query)
            }
            contentType(ContentType.Application.Json)
        }.body()
    }
}