package ru.maxdexter.mynews.data.entity.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Bookmark( @PrimaryKey(autoGenerate = true)
                var id: Int = 0,
                val title: String = "",
                val description: String = "",
                val publishedAt: String = " ",
                val sourceName: String,
                val url: String = "",
                val urlToImage: String = "") 





