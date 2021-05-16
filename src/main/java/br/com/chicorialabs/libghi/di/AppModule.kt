package br.com.chicorialabs.libghi.di

import br.com.chicorialabs.libghi.FilmListViewModel
import br.com.chicorialabs.libghi.repository.FilmRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { (filmRepository: FilmRepository) -> FilmListViewModel(filmRepository) }
}