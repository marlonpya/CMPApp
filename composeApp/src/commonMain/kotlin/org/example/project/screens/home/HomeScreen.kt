package org.example.project.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import composemultiplatformcourse.composeapp.generated.resources.Res
import composemultiplatformcourse.composeapp.generated.resources.app_name
import composemultiplatformcourse.composeapp.generated.resources.clone
import composemultiplatformcourse.composeapp.generated.resources.delete
import composemultiplatformcourse.composeapp.generated.resources.item_cloned
import composemultiplatformcourse.composeapp.generated.resources.item_deleted
import composemultiplatformcourse.composeapp.generated.resources.more_options
import composemultiplatformcourse.composeapp.generated.resources.show_grid_view
import kotlinx.serialization.Serializable
import org.example.project.data.Item
import org.jetbrains.compose.resources.stringResource


@Serializable
object Home

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onItemClick: (Item) -> Unit,
    viewModel: HomeViewModel = viewModel { HomeViewModel() }
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var isGrid by remember { mutableStateOf(false) }
    val isGridIcon by remember { derivedStateOf { if (isGrid) Icons.AutoMirrored.Default.ViewList else Icons.Default.GridView } }
    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.state.notification?.let {
        val stringRes =
            if (it.action == Action.CLONE) Res.string.item_cloned else Res.string.item_deleted
        val message = stringResource(stringRes, it.title)
        LaunchedEffect(it.id) {
            snackbarHostState.showSnackbar(message)
            viewModel.clearNotification()
        }
    }
    Scaffold(
        topBar = {
            HomeTopAppBar(
                isGridIcon = isGridIcon,
                onGridClick = {
                    isGrid = !isGrid
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, null)
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        AnimatedContent(isGrid) { isGrid ->
            if (isGrid) {
                HomeGridScreen(
                    items = viewModel.state.items,
                    onItemClick = onItemClick,
                    onActionClick = viewModel::onAction,
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                HomeListScreen(
                    items = viewModel.state.items,
                    onItemClick = onItemClick,
                    onActionClick = viewModel::onAction,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun MoreActionsIconButton(onActionClick: (Action) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(Res.string.more_options)
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.clone)) },
                onClick = {
                    expanded = false
                    onActionClick.invoke(Action.CLONE)
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.delete)) },
                onClick = {
                    expanded = false
                    onActionClick.invoke(Action.DELETE)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    isGridIcon: ImageVector,
    onGridClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = { Text(stringResource(Res.string.app_name)) },
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = { onGridClick.invoke() }) {
                Icon(
                    imageVector = isGridIcon,
                    contentDescription = stringResource(Res.string.show_grid_view)
                )
            }
        }
    )
}