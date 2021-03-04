package ru.maxdexter.mynews.data.entity.api

data class Article(
    val author: String?= null,
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: Source?,
    val title: String? = "",
    val url: String? = "",
    val urlToImage: String? = ""
)