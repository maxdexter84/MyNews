package ru.maxdexter.mynews.ui.viewmodels.newsviewmodel

import androidx.lifecycle.ViewModel
import ru.maxdexter.mynews.domain.repository.INewsRepository

class NewsViewModel(val repository: INewsRepository): ViewModel() {
}