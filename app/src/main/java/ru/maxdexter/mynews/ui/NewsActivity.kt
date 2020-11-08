package ru.maxdexter.mynews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_news.*
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_news)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)


    }
}