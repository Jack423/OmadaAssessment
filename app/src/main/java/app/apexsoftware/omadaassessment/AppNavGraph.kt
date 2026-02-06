package app.apexsoftware.omadaassessment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.apexsoftware.omadaassessment.ImageViewerArgs.IMAGE_ID_ARG
import app.apexsoftware.omadaassessment.data.Photo
import app.apexsoftware.omadaassessment.views.image_detail.ImageDetailView
import app.apexsoftware.omadaassessment.views.images.ImagesScreen
import kotlinx.coroutines.CoroutineScope
import kotlin.text.get

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = ImageViewerDestinations.IMAGES_ROUTE,
    navActions: ImageViewerNavigationActions = remember(navController) {
        ImageViewerNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            ImageViewerDestinations.IMAGES_ROUTE,
        ) { _ ->
            ImagesScreen(
                onSelectImage = { image -> navActions.navigateToImageDetail(image)}
            )
        }
        composable(
            ImageViewerDestinations.IMAGE_DETAIL_ROUTE
        ) { _ ->
            val image = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Photo>("image") ?: return@composable

            ImageDetailView(image = image)
        }
    }
}