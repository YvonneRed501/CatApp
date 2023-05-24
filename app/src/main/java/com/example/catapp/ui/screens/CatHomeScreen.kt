package com.example.catapp.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.catapp.R
import com.example.catapp.data.Cat
import com.example.catapp.model.Dog

@Composable
fun CatHomeScreen(
    dogsUiState: DogsUiState,
    cats: List<Cat> = listOf(),
    catViewModel: CatViewModel,
    onCatSelected: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column() {
        TopCats(catViewModel = catViewModel, onCatSelected = onCatSelected, cats = cats)
        Spacer(Modifier.height(24.dp))
        when (dogsUiState) {
            is DogsUiState.Loading -> LoadingScreen(modifier)
            is DogsUiState.Success -> PhotoGrid(dogsUiState.dogData, modifier)
            is DogsUiState.Error -> ErrorScreen(modifier)
        }

    }

}


@Composable
fun TopCats(
    cats: List<Cat> = listOf(),
    catViewModel: CatViewModel,
    onCatSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow() {
        itemsIndexed(cats) { position,
                             item ->
            Image(
                modifier = modifier
                    .size(128.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        catViewModel.catSelected(position)
                        onCatSelected()
                    },
                contentScale = ContentScale.Crop,
                painter = painterResource(id = item.imageRes),
                contentDescription = stringResource(
                    id = item.nameRes
                )
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = "Loading Image"
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))

    }
}

@Composable
fun PhotoGrid(photos: List<String>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(photos) { dog ->
            DogsCard(photo = dog)
        }
    }

}

@Composable
fun DogsCard(photo: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.size(100.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo)
                .error(R.drawable.ic_connection_error)
                .build(),
            contentDescription = stringResource(R.string.default_value),
            contentScale = ContentScale.FillBounds
        )
    }
}


