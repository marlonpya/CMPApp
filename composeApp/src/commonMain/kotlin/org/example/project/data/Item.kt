package org.example.project.data

data class Item(
    val id: Int,
    val title: String,
    val subTitle: String,
    val thumb: String
)

val itemList = (1..1000).map {
    Item(
        id = it,
        title = "Title $it",
        subTitle = "Sub title $it",
        //thumb = "https://loremflickr.com/400/400?lock=$it"
        thumb = "https://picsum.photos/id/$it/200/300"
    )
}