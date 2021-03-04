package ru.maxdexter.mynews.domain.models

sealed class Resource {
    data class Success<T>(val data: List<T>) : Resource()
    data class Error(val message: String) : Resource()
    object Loading : Resource()
}