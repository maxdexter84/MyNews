package ru.maxdexter.mynews.settings

import android.app.Activity
import android.content.Context


class AppPreferences(context: Context) {

    val sharedPreferences = context.getSharedPreferences("settings", Activity.MODE_PRIVATE)

    //Сохранение настроек
    //Чтенеие настроек
    var isDarkTheme: Boolean
        get() =// Если настройка не найдена , то берется параметр по умолчанию
            sharedPreferences.getBoolean(IsDarkThem, false)
        set(isDarkTheme) {
            sharedPreferences.edit().putBoolean(IsDarkThem, isDarkTheme).apply()
            //Параметры сохраняются посредствам специального класса editor
        }

    companion object {
        //Имя параметра в настройках
        private const val IsDarkThem = "IS_DARK_THEM"
    }

}

