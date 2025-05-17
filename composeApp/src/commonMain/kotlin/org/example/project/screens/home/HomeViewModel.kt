package org.example.project.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import org.example.project.data.Item
import org.example.project.data.itemList
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

enum class Action { CLONE, DELETE }

class HomeViewModel : ViewModel() {
    var state by mutableStateOf(UiState(itemList))
        private set

    private var nextKey = state.items.size

    data class UiState(
        val items: List<Item> = emptyList(),
        val notification: Notification? = null
    )

    data class Notification(
        val id: Int,
        val action: Action,
        val title: String
    )

    @OptIn(ExperimentalUuidApi::class)
    fun onAction(action: Action, index: Int) {
        val newItems = state.items.toMutableStateList()
        val item = newItems[index]
        state = when (action) {
            Action.CLONE -> {
                UiState(
                    items = newItems.apply {
                        add(index, get(index).copy(id = nextKey++))
                    },
                    notification = Notification(Uuid.random().hashCode(),action, item.title)
                )
            }

            Action.DELETE -> {
                UiState(
                    items = newItems.apply { removeAt(index) },
                    notification = Notification(Uuid.random().hashCode(),action, item.title)
                )
            }
        }
    }

    fun clearNotification() {
        state = state.copy(notification = null)
    }
}