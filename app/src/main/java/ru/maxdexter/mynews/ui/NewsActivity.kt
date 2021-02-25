package ru.maxdexter.mynews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.databinding.ActivityNewsBinding
import ru.maxdexter.mynews.data.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.settings.AppPreferences
import ru.maxdexter.mynews.ui.viewmodels.newsviewmodel.NewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.newsviewmodel.NewsViewModelFactory

class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private var isDarkTheme: Boolean = false
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        isDarkTheme = AppPreferences(this).isDarkTheme
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_news)

        val repository = NewsRepository(ArticleDatabase.invoke(this).getArticleDao(), RetrofitInstance.api)
        val viewModelFactory = NewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NewsViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)


    }
}