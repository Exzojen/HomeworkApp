package com.example.hwapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.hwapp.events.MainUiEvent
import com.example.hwapp.theme.AppTheme
import com.example.hwapp.theme.fontSizeMainCompose
import com.example.hwapp.theme.paddingMainCompose
import com.example.hwapp.theme.paddingSmallCompose
import com.example.hwapp.viewmodels.MainViewModel
import com.example.hwapp.viewmodels.VKPostItem
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import hwapp.composeapp.generated.resources.Res
import hwapp.composeapp.generated.resources.compose_multiplatform

@Composable
fun MainScreen(
    username: String = "Гость",
    viewModel: MainViewModel = viewModel(),
    onExitApp: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is MainUiEvent.ExitApp -> onExitApp()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        MainScreenHeader(username = username)

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            MainScreenFeed(
                items = state.items,
                modifier = Modifier.weight(1f)
            )
        }

        MainScreenFooter(onBackClick = { viewModel.onBackPressed() })
    }
}

@Composable
private fun MainScreenHeader(username: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingMainCompose),
        color = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMainCompose),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Добро пожаловать, $username!",
                fontSize = fontSizeMainCompose,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(paddingSmallCompose))
            Text(
                text = "Новости и обновления",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun MainScreenFeed(
    items: List<VKPostItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(paddingMainCompose),
        verticalArrangement = Arrangement.spacedBy(paddingMainCompose)
    ) {
        items(items = items, key = { it.id }) { item ->
            VKPostCard(item = item)
        }
    }
}

@Composable
private fun VKPostCard(
    item: VKPostItem,
    modifier: Modifier = Modifier
) {
    var isLiked by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.large,
        shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = item.author,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(paddingMainCompose)
            )

            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(Res.drawable.compose_multiplatform),
                error = painterResource(Res.drawable.compose_multiplatform)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMainCompose)
            ) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(paddingSmallCompose))
                Text(
                    text = item.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    maxLines = 3
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingSmallCompose),
                horizontalArrangement = Arrangement.spacedBy(paddingSmallCompose)
            ) {
                ActionButtonWithImage(
                    iconUrl = if (isLiked)
                        "https://example.com/like_active.png"
                    else
                        "https://example.com/like_inactive.png",
                    label = "Нравится",
                    onClick = { isLiked = !isLiked },
                    modifier = Modifier.weight(1f)
                )
                ActionButtonWithImage(
                    iconUrl = "https://example.com/comment.png",
                    label = "Комментарии",
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ActionButtonWithImage(
    iconUrl: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        AsyncImage(
            model = iconUrl,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            contentScale = ContentScale.Fit,
            placeholder = painterResource(Res.drawable.compose_multiplatform),
            error = painterResource(Res.drawable.compose_multiplatform)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun MainScreenFooter(onBackClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMainCompose),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Выход",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(
        )
    }
}