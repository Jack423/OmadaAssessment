package app.apexsoftware.omadaassessment

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import app.apexsoftware.omadaassessment.ImageViewerArgs.IMAGE_ID_ARG
import app.apexsoftware.omadaassessment.ImageViewerDestinations.IMAGES_ROUTE
import app.apexsoftware.omadaassessment.ImageViewerDestinations.IMAGE_DETAIL_ROUTE
import app.apexsoftware.omadaassessment.ImageViewerScreens.IMAGES_SCREEN
import app.apexsoftware.omadaassessment.data.Photo

private object ImageViewerScreens {
    const val IMAGES_SCREEN = "images"
}

object ImageViewerArgs {
    const val IMAGE_ID_ARG = "image"
}

object ImageViewerDestinations {
    const val IMAGES_ROUTE = IMAGES_SCREEN
    const val IMAGE_DETAIL_ROUTE = "$IMAGES_SCREEN/$IMAGE_ID_ARG"
}

class ImageViewerNavigationActions(private val navController: NavController) {

    fun navigateToImages() {
        navController.navigate(IMAGES_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToImageDetail(image: Photo) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.set("image", image)
        navController.navigate(IMAGE_DETAIL_ROUTE)
    }

}