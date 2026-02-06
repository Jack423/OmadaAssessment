package app.apexsoftware.omadaassessment.views.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apexsoftware.omadaassessment.data.Photo
import app.apexsoftware.omadaassessment.repository.image.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ImagesViewState(
    val page: Int = 0,
    val isLoading: Boolean = false,
    val extras: String? = null,
    val images: List<Photo> = emptyList(),
    val error: String? = null,
)

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ImagesViewState())
    val uiState: StateFlow<ImagesViewState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val flickrResponse = imageRepository.getRecentImages(page = _uiState.value.page)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        page = flickrResponse.photos.page,
                        images = flickrResponse.photos.photo,
                        error = null
                    )
                }
                Timber.i("Updated state with ${flickrResponse.photos.photo.size} images")
            } catch (e: Exception) {
                Timber.e(e);
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun getNextPage() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val flickrResponse = imageRepository.getRecentImages(page = _uiState.value.page + 1)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        page = flickrResponse.photos.page,
                        images = _uiState.value.images + flickrResponse.photos.photo,
                        error = null
                    )
                }
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val flickrResponse = imageRepository.searchImages(query)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        page = flickrResponse.photos.page,
                        images = flickrResponse.photos.photo,
                        error = null
                    )
                }
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}