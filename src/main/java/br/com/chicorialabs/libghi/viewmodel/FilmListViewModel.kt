package br.com.chicorialabs.libghi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.repository.FilmRepository
import kotlinx.coroutines.launch

class FilmListViewModel(private val filmRepository: FilmRepository): ViewModel() {

    private val _filmList = MutableLiveData<List<Film>>()
    val filmList: LiveData<List<Film>>
        get() = _filmList

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val _snackbar = MutableLiveData<String?>()
    val snackbar: LiveData<String?>
        get() = _snackbar

    fun init() {
        launchDataLoad { getAllFilms() }
    }


    private suspend fun getAllFilms() {

        try {
            _filmList.postValue(filmRepository.loadData())
        } catch (error: FilmLoadError) {
            Log.e("lib_ghi", "getAllFilms: Ocorreu um erro do tipo FilmLoadError", )
            _snackbar.value = error.message
        }
    }
    
    fun onSnackBarShown() {
        _snackbar.value = null
    }

    private fun launchDataLoad(block: suspend () -> Unit){
        viewModelScope.launch {
            try {
                _progressBar.value = true
                block()
            } catch (ex: Exception){
                Log.e("lig_ghi", "launchDataLoad: Ocorreu um erro", )
            } finally {
                _progressBar.value = false
            }
        }
    }



}

class FilmLoadError(override val message: String, cause: Throwable): Throwable()
