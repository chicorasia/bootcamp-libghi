package br.com.chicorialabs.libghi.repository

import br.com.chicorialabs.libghi.FilmLoadError
import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.model.FilmResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class FilmRepository {

    suspend fun loadData(): ArrayList<Film>? {
        val client: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://ghibliapi.herokuapp.com/films")
            .build()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                parseJsonToResult(response.body?.string()).films
            } catch (cause: Throwable) {
                throw FilmLoadError("Ocorreu um erro", cause)
            }
        } as? ArrayList<Film>


    }

    private fun parseJsonToResult(json: String?): FilmResult {

        val turnsType = object : TypeToken<List<Film>>() {}.type
        return FilmResult(Gson().fromJson(json, turnsType))

    }

}