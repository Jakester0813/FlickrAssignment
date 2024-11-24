package com.jake.flickrassignment.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.jake.flickrassignment.data.getImageNameForContentDescription
import com.jake.flickrassignment.main.MainViewModel

@Composable
fun ImageDetailScreen(viewModel: MainViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageData = viewModel.selectedImage
        val context = LocalContext.current
        AsyncImage(
            modifier = Modifier
                .size(260.dp)
                .aspectRatio(1f)
                .fillMaxSize(),
            model = ImageRequest.Builder(context)
                .data(imageData.imgUrl)
                .transformations(RoundedCornersTransformation(20f))
                .build(),
            contentDescription = imageData.getImageNameForContentDescription()
        )
        Text(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            text = imageData.title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        )
        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            text = imageData.publishedDate,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            text = AnnotatedString.fromHtml(imageData.description),
            fontSize = 14.sp
        )
    }
    BackHandler {
        navController.popBackStack()
    }
}