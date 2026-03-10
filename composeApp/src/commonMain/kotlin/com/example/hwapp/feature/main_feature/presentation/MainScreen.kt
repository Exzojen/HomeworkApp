package com.example.hwapp.feature.main_feature.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.hwapp.events.MainUiEvent
import composeResources.AppTheme
import composeResources.fontSizeMainCompose
import composeResources.paddingMainCompose
import composeResources.paddingSmallCompose
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import hwapp.composeapp.generated.resources.Res
import hwapp.composeapp.generated.resources.compose_multiplatform
// Здесь появятся импорты ваших новых иконок после сборки проекта, например:
import hwapp.composeapp.generated.resources.ic_home
import hwapp.composeapp.generated.resources.ic_inbox
import hwapp.composeapp.generated.resources.ic_explore
import hwapp.composeapp.generated.resources.ic_profile

@Composable
fun MainScreen(
    username: String = "Developer",
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

    Scaffold(
        bottomBar = {
            GithubBottomNavigation(
                currentTab = state.currentTab,
                onTabSelected = { viewModel.onTabSelected(it) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            MainScreenHeader(username = username, currentTab = state.currentTab)

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                when (state.currentTab) {
                    GithubTab.HOME -> {
                        MainScreenFeed(
                            items = state.items,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    GithubTab.INBOX, GithubTab.EXPLORE, GithubTab.PROFILE -> {
                        StubScreen(tabName = state.currentTab.name, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun MainScreenHeader(username: String, currentTab: GithubTab) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMainCompose),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (currentTab) {
                    GithubTab.HOME -> "Home"
                    GithubTab.INBOX -> "Inbox"
                    GithubTab.EXPLORE -> "Explore"
                    GithubTab.PROFILE -> username
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun MainScreenFeed(
    items: List<GithubRepoItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(paddingMainCompose),
        verticalArrangement = Arrangement.spacedBy(paddingMainCompose)
    ) {
        items(items = items, key = { it.id }) { item ->
            GithubRepoCard(item = item)
        }
    }
}

@Composable
private fun GithubRepoCard(
    item: GithubRepoItem,
    modifier: Modifier = Modifier
) {
    var isStarred by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMainCompose)
        ) {
            // Заголовок карточки с аватаркой и названием
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = item.avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(Res.drawable.compose_multiplatform),
                    error = painterResource(Res.drawable.compose_multiplatform)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${item.owner} / ",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Описание
            Text(
                text = item.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Статистика (Язык, Звезды, Форки) и кнопка Star
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Имитация кружочка цвета языка программирования
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (item.language == "Kotlin") Color(0xFFB125EA) else Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = item.language, fontSize = 12.sp)
                    }
                    Text(text = "⭐ ${item.stars}", fontSize = 12.sp)
                    Text(text = "🔀 ${item.forks}", fontSize = 12.sp)
                }

                Button(
                    onClick = { isStarred = !isStarred },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isStarred) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(text = if (isStarred) "Starred" else "Star", fontSize = 12.sp)
                }
            }
        }
    }
}

// Заглушка для вкладок Inbox, Explore, Profile
@Composable
private fun StubScreen(tabName: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$tabName Screen Content",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun GithubBottomNavigation(
    currentTab: GithubTab,
    onTabSelected: (GithubTab) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_home),
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") },
            selected = currentTab == GithubTab.HOME,
            onClick = { onTabSelected(GithubTab.HOME) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_inbox),
                    contentDescription = "Inbox"
                )
            },
            label = { Text("Inbox") },
            selected = currentTab == GithubTab.INBOX,
            onClick = { onTabSelected(GithubTab.INBOX) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_explore),
                    contentDescription = "Explore"
                )
            },
            label = { Text("Explore") },
            selected = currentTab == GithubTab.EXPLORE,
            onClick = { onTabSelected(GithubTab.EXPLORE) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_profile),
                    contentDescription = "Profile"
                )
            },
            label = { Text("Profile") },
            selected = currentTab == GithubTab.PROFILE,
            onClick = { onTabSelected(GithubTab.PROFILE) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}