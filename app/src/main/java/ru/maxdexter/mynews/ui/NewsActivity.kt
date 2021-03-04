package ru.maxdexter.mynews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.databinding.ActivityNewsBinding
import ru.maxdexter.mynews.data.db.AppDatabase
import ru.maxdexter.mynews.domain.repository.ILocalSource
import ru.maxdexter.mynews.domain.repository.IRemoteSource
import ru.maxdexter.mynews.repository.LocalSourceImpl
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.repository.RemoteSourceImpl
import ru.maxdexter.mynews.settings.AppPreferences
import ru.maxdexter.mynews.ui.viewmodels.newsviewmodel.NewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.newsviewmodel.NewsViewModelFactory

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val navController: NavController by lazy {
      val navHostFragment =  supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navHostFragment.navController
    }
    private val repository: NewsRepository by lazy{
        val localSourceImpl: ILocalSource = LocalSourceImpl(AppDatabase.invoke(this).bookmarkDao())
        val remoteSourceImpl: IRemoteSource = RemoteSourceImpl(RetrofitInstance.api)
        NewsRepository(localSourceImpl, remoteSourceImpl)
    }
    private val viewModelFactory: NewsViewModel by lazy {
        ViewModelProvider(this,NewsViewModelFactory(repository)).get(NewsViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        when(AppPreferences(this).isDarkTheme){
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_news)
       
     

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}