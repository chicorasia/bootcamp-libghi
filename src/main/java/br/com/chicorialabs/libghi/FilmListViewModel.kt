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


    fun init() {
        getAllFilms()
    }

    private fun getAllFilms() {

        _filmList.postValue(FilmRepository().loadData())
    }

}