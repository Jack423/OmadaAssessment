package app.apexsoftware.omadaassessment.repository.image

import app.apexsoftware.omadaassessment.data.FlickrResponse

interface ImageRepository {
    suspend fun getRecentImages(
        extras: String? = null,
        perPage: Int? = 50,
        page: Int? = null
    ): FlickrResponse

    suspend fun searchImages(
        query: String,
    ): FlickrResponse
}