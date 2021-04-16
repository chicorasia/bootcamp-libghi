package br.com.chicorialabs.libghi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.repository.FilmRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FilmListViewModel: ViewModel() {

    private val _filmList = MutableLiveData<ArrayList<Film>>()
    val filmList: LiveData<ArrayList<Film>>
        get() = _filmList


    fun init() {
        launchDataLoad { getAllFilms() }
    }

    private suspend fun getAllFilms() {
        viewModelScope.launch {
            try{
                _filmList.value = FilmRepository().loadData()
            } catch (error: FilmLoadError){
                Log.e("lib_ghi", "Ocorreu um erro do tipo FilmLoadError")
            }
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }
}

class FilmLoadError(override val message: String, cause: Throwable) : Throwable(cause)