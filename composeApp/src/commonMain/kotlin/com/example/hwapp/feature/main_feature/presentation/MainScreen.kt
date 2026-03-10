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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.hwapp.events.MainUiEvent
import composeResources.AppTheme
import composeResources.StringConstants
import composeResources.StringConstants.MainScreen.mainScreenPagingErrorLabel
import composeResources.StringConstants.MainScreen.stubScreenContent
import composeResources.height_modifier_description
import composeResources.height_modifier_large
import composeResources.height_modifier_medium
import composeResources.height_modifier_small
import composeResources.paddingMainCompose
import composeResources.repo_description_labels_font
import composeResources.tab_list_font_size
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import hwapp.composeapp.generated.resources.Res
import hwapp.composeapp.generated.resources.compose_multiplatform
import hwapp.composeapp.generated.resources.ic_home
import hwapp.composeapp.generated.resources.ic_inbox
import hwapp.composeapp.generated.resources.ic_explore
import hwapp.composeapp.generated.resources.ic_fork
import hwapp.composeapp.generated.resources.ic_profile
import hwapp.composeapp.generated.resources.ic_star

@Composable
fun MainScreen(
    username: String = StringConstants.MainScreen.MAIN_SCREEN_USERNAME_PLACEHOLDER,
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
            modifier = Modifier.fillMaxSize().padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            MainScreenHeader(
                username = state.username,
                currentTab = state.currentTab
            )

            if (state.currentTab == GithubTab.HOME) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { viewModel.onQueryChanged(it) },
                    modifier = Modifier.fillMaxWidth().padding(paddingMainCompose),
                    placeholder = { Text(StringConstants.MainScreen.MAIN_SCREEN_REPO_SEARCHBAR_TEXT) },
                    singleLine = true
                )

                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    when {
                        state.isLoading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }

                        state.errorMessage != null && state.items.isEmpty() -> {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Ошибка: ${state.errorMessage}",
                                    color = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.height(height_modifier_small))
                                Button(onClick = { viewModel.retry() }) { Text(StringConstants.MainScreen.MAIN_SCREEN_RETRY_ACTION) }
                            }
                        }

                        state.isListEmpty -> {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.compose_multiplatform),
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.height(height_modifier_medium))
                                Text(StringConstants.MainScreen.MAIN_SCREEN_SEARCH_ISBLANK)
                            }
                        }

                        else -> {
                            MainScreenFeed(
                                items = state.items,
                                isPaging = state.isPaging,
                                pagingError = if (state.items.isNotEmpty()) state.errorMessage else null,
                                onLoadMore = { viewModel.loadNextPage() },
                                onRetryPaging = { viewModel.loadNextPage() }
                            )
                        }
                    }
                }
            } else {
                StubScreen(tabName = state.currentTab.name, modifier = Modifier.weight(1f))
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
                    GithubTab.HOME -> StringConstants.MainScreen.MAIN_SCREEN_TAB_HOME
                    GithubTab.INBOX -> StringConstants.MainScreen.MAIN_SCREEN_TAB_INBOX
                    GithubTab.EXPLORE -> StringConstants.MainScreen.MAIN_SCREEN_TAB_EXPLORE
                    GithubTab.PROFILE -> username
                },
                fontSize = tab_list_font_size,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun MainScreenFeed(
    items: List<GithubRepoItem>,
    isPaging: Boolean,
    pagingError: String?,
    onLoadMore: () -> Unit,
    onRetryPaging: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()

    LaunchedEffect(listState.layoutInfo.visibleItemsInfo) {
        val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
        if (lastVisibleItemIndex >= items.size - 2) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(paddingMainCompose),
        verticalArrangement = Arrangement.spacedBy(paddingMainCompose)
    ) {
        items(items = items, key = { it.id }) { item ->
            GithubRepoCard(item = item)
        }

        item {
            if (isPaging) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (pagingError != null) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        mainScreenPagingErrorLabel(pagingError),
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = onRetryPaging) { Text(StringConstants.MainScreen.MAIN_SCREEN_RETRY_ACTION) }
                }
            }
        }
    }
}

@Composable
fun RepoStatItem(
    icon: Painter,
    text: String,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = iconTint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = repo_description_labels_font,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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

            Text(
                text = item.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(height_modifier_description))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (item.language == "Kotlin") Color(0xFFB125EA) else Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = item.language, fontSize = repo_description_labels_font)
                    }
                    RepoStatItem(
                        icon = painterResource(Res.drawable.ic_star),
                        text = item.stars.toString(),
                        iconTint = Color(0xFFFFC107)
                    )
                    RepoStatItem(
                        icon = painterResource(Res.drawable.ic_fork),
                        text = item.forks.toString()
                    )
                }
            }
            Button(
                onClick = { isStarred = !isStarred },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isStarred) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier
                    .height(height_modifier_large)
                    .align(Alignment.End)
            ) {
                Text(text = if (isStarred) "Starred" else "Star", fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun StubScreen(tabName: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stubScreenContent(tabName),
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
                    contentDescription = StringConstants.MainScreen.MAIN_SCREEN_TAB_HOME
                )
            },
            label = { Text(StringConstants.MainScreen.MAIN_SCREEN_TAB_HOME) },
            selected = currentTab == GithubTab.HOME,
            onClick = { onTabSelected(GithubTab.HOME) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_inbox),
                    contentDescription = StringConstants.MainScreen.MAIN_SCREEN_TAB_INBOX
                )
            },
            label = { Text(StringConstants.MainScreen.MAIN_SCREEN_TAB_INBOX) },
            selected = currentTab == GithubTab.INBOX,
            onClick = { onTabSelected(GithubTab.INBOX) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_explore),
                    contentDescription = StringConstants.MainScreen.MAIN_SCREEN_TAB_EXPLORE
                )
            },
            label = { Text(StringConstants.MainScreen.MAIN_SCREEN_TAB_EXPLORE) },
            selected = currentTab == GithubTab.EXPLORE,
            onClick = { onTabSelected(GithubTab.EXPLORE) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_profile),
                    contentDescription = StringConstants.MainScreen.MAIN_SCREEN_TAB_PROFILE
                )
            },
            label = { Text(StringConstants.MainScreen.MAIN_SCREEN_TAB_PROFILE) },
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