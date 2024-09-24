package com.marossolutions.skeletonmultiplatform.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.marossolutions.skeletonmultiplatform.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import skeletonmultiplatform.composeapp.generated.resources.Res
import skeletonmultiplatform.composeapp.generated.resources.airport_logo
import skeletonmultiplatform.composeapp.generated.resources.welcome_button_text
import skeletonmultiplatform.composeapp.generated.resources.welcome_title

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun WelcomeScreen(viewModel: WelcomeViewModel = koinViewModel()) {

    WelcomeScreenContent(
        navigateToNextScreen = viewModel::navigateToNextScreen
    )
}

@Composable
private fun WelcomeScreenContent(navigateToNextScreen: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(Res.string.welcome_title), fontSize = 32.sp)
        Image(
            painter = painterResource(Res.drawable.airport_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .size(144.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = navigateToNextScreen) {
            Text(text = stringResource(Res.string.welcome_button_text))
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenContentPreview() {
    AppTheme {
        WelcomeScreenContent {}
    }
}
