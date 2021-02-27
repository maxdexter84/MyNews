package ru.maxdexter.mynews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.databinding.ActivityNewsBinding
import ru.maxdexter.mynews.data.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.settings.AppPreferences
import ru.maxdexter.mynews.ui.fragments.BottomNavDrawerMenuDirections
import ru.maxdexter.mynews.ui.viewmodels.newsviewmodel.NewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.newsviewmodel.NewsViewModelFactory

class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityNewsBinding
    private val navController: NavController by lazy {
      val navHostFragment =  supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navHostFragment.navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        when(AppPreferences(this).isDarkTheme){
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_news)

        val repository = NewsRepository(ArticleDatabase.invoke(this).getArticleDao(), RetrofitInstance.api)
        val viewModelFactory = NewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NewsViewModel::class.java)

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}