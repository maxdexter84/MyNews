package ru.maxdexter.mynews.data.db

import androidx.room.TypeConverter
import ru.maxdexter.mynews.data.entity.api.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(str: String): Source {
        return Source(str, str)
    }
}