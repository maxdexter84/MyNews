package ru.maxdexter.mynews.ui.entity
import java.io.Serializable

data class UIModel(
                    var id: Int = 0,
                    val title: String = "",
                    val description: String = "",
                    val publishedAt: String = "",
                    val sourceName: String,
                    val url: String = "",
                    val urlToImage: String = "") : Serializable