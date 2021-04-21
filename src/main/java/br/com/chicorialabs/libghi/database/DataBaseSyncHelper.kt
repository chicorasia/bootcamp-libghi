package br.com.chicorialabs.libghi.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.widget.Toast
import br.com.chicorialabs.libghi.model.Film

class DataBaseSyncHelper(private val context: Context) {

    fun updateDb(filmList: List<Film>) {
        val cursor = context.contentResolver.query(FilmsProvider.URI_FILMS, null, null, null, "${BaseColumns._ID}")

        when (cursor?.count) {
            filmList.size -> {
                Toast.makeText(
                    context,
                    "DB está sincronizada!\nContagem na DB: " +
                            "${cursor?.count}\nContagem no repo: ${filmList.size}",
                    Toast.LENGTH_LONG
                ).show()
            }
            0 -> {
                addAllFilmsFromRepo(filmList)
            }
            else -> {
                Toast.makeText(
                    context,
                    "DB desatualizada\nContagem na DB: " +
                            "${cursor?.count}\nContagem no repo: ${filmList.size}",
                    Toast.LENGTH_LONG
                ).show()
                filmList.forEach { film ->
                    cursor?.moveToNext()
                    if (cursor?.getString(cursor.getColumnIndex(FilmsDataBaseHelper.TITLE)) != film.title) {
                        val values = ContentValues().apply {
                            put(FilmsDataBaseHelper.TITLE, film.title)
                            put(FilmsDataBaseHelper.ORIGINAL_TITLE, film.original_title)
                            put(FilmsDataBaseHelper.ORIGINAL_TITLE_ROMANISED, film.original_title_romanised)
                            put(FilmsDataBaseHelper.DIRECTOR, film.director)
                            put(FilmsDataBaseHelper.RELEASE_DATE, film.release_date)
                            put(FilmsDataBaseHelper.RUNNING_TIME, film.running_time)
                        }
                        context.contentResolver.insert(FilmsProvider.URI_FILMS, values)
                    }
                }
            }
        }

    }

    private fun addAllFilmsFromRepo(filmList: List<Film>) {
        filmList.forEach { film ->
            val values = ContentValues().apply {
                put(FilmsDataBaseHelper.TITLE, film.title)
                put(FilmsDataBaseHelper.ORIGINAL_TITLE, film.original_title)
                put(FilmsDataBaseHelper.ORIGINAL_TITLE_ROMANISED, film.original_title_romanised)
                put(FilmsDataBaseHelper.DIRECTOR, film.director)
                put(FilmsDataBaseHelper.RELEASE_DATE, film.release_date)
                put(FilmsDataBaseHelper.RUNNING_TIME, film.running_time)
            }
            context.contentResolver.insert(FilmsProvider.URI_FILMS, values)
        }
    }

}