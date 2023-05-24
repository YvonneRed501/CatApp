package com.example.catapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catapp.R
import com.example.catapp.data.CatRepository.cats


@Composable
fun CatContentScreen(
    catViewModel: CatViewModel
) {
    val cat by catViewModel.cat.collectAsState()

    // need to change to a LazyColumn? or make it scrollable
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = cat?.nameRes ?: R.string.default_value),
            fontSize = 50.sp,
            textAlign = TextAlign.Justify,
        )
        Spacer(Modifier.height(24.dp))
        Image(
            painterResource(id = cat?.imageRes ?: R.drawable.catsilhouette),
            null,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                .clip(
                RoundedCornerShape(10.dp)

            )
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(id = cat?.descriptionRes ?: R.string.default_value),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(16.dp)
        )
    }
}



