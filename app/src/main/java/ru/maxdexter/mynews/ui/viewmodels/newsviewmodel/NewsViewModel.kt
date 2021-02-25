package ru.maxdexter.mynews.ui.viewmodels.newsviewmodel

import androidx.lifecycle.ViewModel
import ru.maxdexter.mynews.repository.NewsRepository

class NewsViewModel(val repository: NewsRepository): ViewModel() {
}