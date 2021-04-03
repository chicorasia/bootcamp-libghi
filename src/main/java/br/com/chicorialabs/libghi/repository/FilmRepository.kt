package br.com.chicorialabs.libghi.repository

import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.model.FilmResult
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class FilmRepository {

    fun loadData() : List<Film>{
        val client: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://ghibliapi.herokuapp.com/films")
            .build()

        val response = client.newCall(request).execute()
        val result = parseJsonToResult(response.body?.string())

        return result.films

    }

    private fun parseJsonToResult(string: String?) =
        Gson().fromJson(string, FilmResult::class.java)

}