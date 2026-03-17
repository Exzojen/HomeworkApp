package com.example.hwapp.feature.greeting_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import composeResources.AppTheme
import composeResources.StringConstants
import composeResources.fontSizeMainCompose
import composeResources.paddingMainCompose
import composeResources.paddingTinyCompose
import org.jetbrains.compose.resources.painterResource
import hwapp.composeapp.generated.resources.Res
import hwapp.composeapp.generated.resources.compose_multiplatform

@Composable
fun GreetingScreen(
    onContinueClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = StringConstants.GreetingScreen.GREETING_SCREEN_GREETING_MESSAGE,
            modifier = Modifier
                .weight(1f)
                .padding(top = paddingMainCompose),
            fontSize = fontSizeMainCompose,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        AsyncImage(
            model = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png",
            contentDescription = null,
            placeholder = painterResource(Res.drawable.compose_multiplatform),
            error = painterResource(Res.drawable.compose_multiplatform),
            modifier = Modifier
                .weight(3f)
                .aspectRatio(16f / 9f)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.padding(top = paddingTinyCompose),
            onClick = onContinueClicked,
        ) {
            Text(StringConstants.GreetingScreen.GREETING_SCREEN_CONTINUE_LABEL)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun GreetingScreenPreview() {
    AppTheme {
        GreetingScreen(onContinueClicked = {})
    }
}