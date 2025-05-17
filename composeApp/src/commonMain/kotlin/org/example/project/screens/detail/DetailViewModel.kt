package org.example.project.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.example.project.data.Item
import org.example.project.data.itemList

class DetailViewModel(private val itemId: Int) : ViewModel() {
    var state by mutableStateOf<Item?>(null)
        private set

    init {
        state = itemList.find { it.id == itemId }
    }

}