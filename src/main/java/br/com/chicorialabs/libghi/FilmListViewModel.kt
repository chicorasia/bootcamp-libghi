package br.com.chicorialabs.libghi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.repository.FilmRepository
import kotlinx.coroutines.launch

class FilmListViewModel: ViewModel() {

    private val _filmList = MutableLiveData<List<Film>>()
    val filmList: LiveData<List<Film>>
        get() = _filmList

//    TODO 001: adicionar um parâmetro _progressBar do tipo Boolean
//    TODO 002: adicionar um parâmtro _snackBar do tipo String com valor inicial null


    fun init() {
        launchDataLoad { getAllFilms() }
    }


    private suspend fun getAllFilms() {

//        TODO 004: Adicionar o comportamento do _snackBar
        try {
            _filmList.postValue(FilmRepository().loadData())
        } catch (error: FilmLoadError) {
            Log.e("lib_ghi", "getAllFilms: Ocorreu um erro do tipo FilmLoadError", )
        }


    }

    private fun launchDataLoad(block: suspend () -> Unit){
        viewModelScope.launch {
//            TODO 003: adicionar um bloco try-catch e os comportamentos para o _progressBar
            //alguma coisa antes
            block()
            //alguma coisa depois
        }
    }



}

class FilmLoadError(override val message: String, cause: Throwable): Throwable()
