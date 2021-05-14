package br.com.chicorialabs.libghi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.chicorialabs.libghi.FilmListViewModel
import br.com.chicorialabs.libghi.repository.FilmRepository

class FilmListViewModelFactory(private val filmRepository: FilmRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FilmListViewModel(filmRepository) as T
    }
}