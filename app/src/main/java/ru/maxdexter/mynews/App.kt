package ru.maxdexter.mynews

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ru.maxdexter.mynews.settings.AppPreferences

class App: Application() {
        private var isDarkTheme = false
    override fun onCreate() {

        super.onCreate()
    }
}