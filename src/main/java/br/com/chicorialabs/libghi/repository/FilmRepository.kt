package br.com.chicorialabs.libghi.repository

import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.model.FilmResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request

class FilmRepository {

//    TODO 004: Modificar loadData() para executar a requisição com o Dispatchers.IO
    fun loadData() : List<Film>{
        val client: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://ghibliapi.herokuapp.com/films")
            .build()

        val response = client.newCall(request).execute()
        val result = parseJsonToResult(response.body?.string())

        return result.films

    }

    private fun parseJsonToResult(json: String?): FilmResult {

        val turnsType = object : TypeToken<List<Film>>(){}.type
        return FilmResult(Gson().fromJson(json, turnsType))

    }

}