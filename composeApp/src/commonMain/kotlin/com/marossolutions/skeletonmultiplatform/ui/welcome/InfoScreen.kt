package com.marossolutions.skeletonmultiplatform.ui.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.marossolutions.skeletonmultiplatform.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import skeletonmultiplatform.composeapp.generated.resources.Res
import skeletonmultiplatform.composeapp.generated.resources.info_button_text
import skeletonmultiplatform.composeapp.generated.resources.info_description
import skeletonmultiplatform.composeapp.generated.resources.info_title

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun InfoScreen(viewModel: InfoViewModel = koinViewModel()) {
    InfoScreenContent(navigateToHome = viewModel::navigateToHome)
}

@Composable
private fun InfoScreenContent(navigateToHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Text(stringResource(Res.string.info_title), fontSize = 32.sp)
        Text(stringResource(Res.string.info_description))
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = navigateToHome,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(Res.string.info_button_text))
        }
    }
}

@Preview
@Composable
private fun InfoScreenContentPreview() {
    AppTheme {
        InfoScreenContent {}
    }
}