package com.fredericho.borutoapp.presentation.screen.details

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.fredericho.borutoapp.util.Constants.BASE_URL
import com.fredericho.borutoapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.fredericho.borutoapp.util.PaletteGenerator.extractColorFromBitmap
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DetailsScreen(
    navController : NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {

    val selectedHero by detailsViewModel.selectedHero.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        detailsViewModel.uiEvent.collectLatest { event ->
            when(event){
                is UiEvent.GeneratorColorPalette -> {
                    val bitmap = convertImageUrlToBitmap(
                        imageUrl = "${BASE_URL}${selectedHero?.image}",
                        context = context
                    )
                    if(bitmap != null){
                        detailsViewModel.setColorPalette(
                            colors = extractColorFromBitmap(
                                bitmap
                            )
                        )
                    }
                }
            }
        }
    }

    val colorPalette by detailsViewModel.colorPalette

    if(colorPalette.isNotEmpty()){
        DetailsContent(
            navController = navController,
            selectedHero = selectedHero,
            colors = colorPalette
        )
    } else {
        detailsViewModel.generateColorPalette()
    }

    Log.d("DetailContent", "\"${BASE_URL}${selectedHero?.image}\"")
}