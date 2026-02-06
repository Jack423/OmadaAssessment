package app.apexsoftware.omadaassessment.views.image_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.apexsoftware.omadaassessment.R
import app.apexsoftware.omadaassessment.data.Photo
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailView(
    image: Photo?,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.image_details)) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    ) { paddingValues ->
        if (image == null) {
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                Text("An Error occurred while loading the image")
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                val url = stringResource(
                    R.string.flickr_template,
                    image.server,
                    image.id,
                    image.secret,
                    "m"
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = image.title,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}