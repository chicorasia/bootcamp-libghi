package br.com.chicorialabs.libghi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.repository.FilmLoadError
import br.com.chicorialabs.libghi.repository.FilmRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FilmListViewModel : ViewModel() {

    private val _filmList = MutableLiveData<List<Film>>()
    val filmList: LiveData<List<Film>>
        get() = _filmList

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val _snackBar = MutableLiveData<String>()
    val snackbar: LiveData<String>
        get() = _snackBar

    suspend fun init() {
        launchDataLoad { getAllFilms() }

    }

    suspend fun getAllFilms() {

        viewModelScope.launch {

            FilmRepository().loadData()?.let {
                _filmList.value = it
            } ?: run {
                _snackBar.value = "Ocorreu um erro!"
            }

        }

    }

    fun onSnackBarShown(){
        _snackBar.value = null
    }

    private fun launchDataLoad(block: suspend () -> Unit) {

        viewModelScope.launch {
            try {
                _progressBar.value = true
                block()
                delay(5000)
            } catch (error: FilmLoadError) {
                _snackBar.value = error.message
            } finally {
                _progressBar.value = false
            }
        }

    }

}