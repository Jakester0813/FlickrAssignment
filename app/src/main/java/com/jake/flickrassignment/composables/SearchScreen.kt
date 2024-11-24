import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.example.flickrassignment.R
import com.jake.flickrassignment.main.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    viewModel: MainViewModel = hiltViewModel()
){
    val currState = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column (modifier = Modifier.fillMaxSize()) {
        var input by rememberSaveable { mutableStateOf("") }
        var prevInput by rememberSaveable { mutableStateOf("") }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .semantics { this.contentDescription = context.getString(R.string.search_hint) },
            value = input,
            onValueChange = {input = it},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.getImages(input)
                },
            ),
            placeholder = {
                Text(
                    stringResource(R.string.search_hint),
                    fontSize = 14.sp
                )
            }
        )

        LaunchedEffect(key1 = input){
            if(input.isEmpty() || input == prevInput) return@LaunchedEffect

            delay(1000)
            viewModel.getImages(input)
            prevInput = input
        }

        if (currState.value.imagesLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(70.dp)
                    .padding(start = 0.dp, top = 80.dp, end = 0.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        } else if (currState.value.errorMessage.isNotEmpty()) {
            Text(
                text = context.getString(R.string.flickr_error, currState.value.errorMessage),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center
            )
        } else if (currState.value.images.isEmpty() && !currState.value.initialLoad) {
            Text(
                text = context.getString(R.string.no_images),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            LazyVerticalGrid (
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, start = 8.dp, bottom = 0.dp, end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val photos = currState.value.images
                items(photos.size) { index: Int ->
                    AsyncImage(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxSize()
                            .clickable { viewModel.onImageClick(index) },
                        model = ImageRequest.Builder(context)
                            .data(photos[index].imgUrl)
                            .transformations(RoundedCornersTransformation(20f))
                            .crossfade(true)
                            .build(),
                        onError = { it ->
                            Log.d(
                                "Image error",
                                " ${it.result.throwable.message!!} Url: ${photos[index].imgUrl}"
                            )
                        },
                        contentDescription = photos[index].title
                    )
                }
            }
        }
    }
}