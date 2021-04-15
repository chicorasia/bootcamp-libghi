package br.com.chicorialabs.libghi.repository

import android.util.Log
import android.widget.Toast
import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.model.FilmResult
import br.com.chicorialabs.libghi.ui.activity.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.lang.NullPointerException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

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
            } catch (ex: UnknownHostException) {
                Log.e("lib_ghi", "loadData: Ocorreu um erro de rede!")
            } catch (ex: SocketTimeoutException){
                Log.e("lib_ghi", "loadData: Erro de Timeout")
            } catch (cause: Throwable){
                Log.e("lib_ghi", "loadData: jogando um FilmLoadError", )
                throw FilmLoadError("Ocorreu um erro ao carregar os dados", cause)
            }
        } as? ArrayList<Film>


    }

    private fun parseJsonToResult(json: String?): FilmResult {

        val turnsType = object : TypeToken<List<Film>>() {}.type
        return FilmResult(Gson().fromJson(json, turnsType))

    }

}

class FilmLoadError(message: String, cause: Throwable?) : Throwable(message, cause)
