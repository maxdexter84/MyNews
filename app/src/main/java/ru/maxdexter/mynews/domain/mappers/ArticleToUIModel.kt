package ru.maxdexter.mynews.domain.mappers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import ru.maxdexter.mynews.data.entity.api.Article
import ru.maxdexter.mynews.ui.entity.UIModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException
import java.util.*

class ArticleToUIModel {
    @RequiresApi(Build.VERSION_CODES.O)
    fun fromArticlesToUIModel(article: Article): UIModel {
        return UIModel(
            title = article.title ?: "",
            description = article.description ?: "",
            publishedAt = fromLongToStringLocalDate(fromStringZoneDateToLongDate(article.publishedAt ?: "")),
            sourceName = article.source?.name ?: "",
            url = article.url ?: "",
            urlToImage = article.urlToImage ?: ""
        )
    }

  
    private fun fromStringZoneDateToLongDate(date: String): Long {
       return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           try {
                val zoneId = ZoneId.systemDefault()
                val instant = ZonedDateTime.parse(date).toInstant()
                val currentDate = ZonedDateTime.of(LocalDateTime.ofInstant(instant, zoneId), zoneId)
                 Date.from(currentDate.toInstant()).time
            }catch (e: DateTimeParseException){
                Log.i("APP_INFO",e.parsedString + e.message)
               0
            }
       } else {
           0
       }
    }

   
    private fun fromLongToStringLocalDate(dateLong: Long): String {
        return try {
                val dateFormat = "yyyy-MM-dd HH:mm:ss"
                val date = Date(dateLong)
                val parser = SimpleDateFormat(dateFormat,Locale.getDefault())
                parser.format(date)
            }catch(e: IllegalArgumentException){
                e.message?.let { Log.i("APP_INFO", it) }
                " "
            }
    }
}