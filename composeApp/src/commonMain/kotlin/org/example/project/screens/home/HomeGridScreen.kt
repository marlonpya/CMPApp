package org.example.project.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.example.project.data.Item

@Composable
fun HomeGridScreen(
    items: List<Item>,
    onItemClick: (Item) -> Unit,
    onActionClick: (Action, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
            Column(
                modifier = Modifier
                    .padding(2.dp)
                    .animateItem()
                    .clickable {
                        onItemClick.invoke(item)
                    }) {
                AsyncImage(
                    model = item.thumb,
                    contentDescription = item.title,
                    modifier = Modifier.aspectRatio(1f)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.title,
                        modifier = Modifier.weight(1f).padding(8.dp)
                    )
                    MoreActionsIconButton {
                        onActionClick(it, index)
                    }
                }
            }
        }
    }
}
