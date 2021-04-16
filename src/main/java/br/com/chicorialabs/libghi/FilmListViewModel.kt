package br.com.chicorialabs.libghi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.repository.FilmRepository

class FilmListViewModel: ViewModel() {

    private val _filmList = MutableLiveData<List<Film>>()
    val filmList: LiveData<List<Film>>
        get() = _filmList


//  TODO 006: invocar o método getAllFilms() a partir da suspend lambda launchDataLoad()

//  TODO 002.1: chamar o método getAllFilms a partir no viewModelScope
    fun init() {
        getAllFilms()
    }

//  TODO 002: transformar getAllFilms() em uma função suspend e bloco try-catch lançando FilmLoadError
    private fun getAllFilms() {

        _filmList.postValue(FilmRepository().loadData())

    }

//    TODO 005: criar a função suspend lambda launchDataLoad( () -> Unit )

//    TODO 003: Criar uma classe FilmLoadError

}