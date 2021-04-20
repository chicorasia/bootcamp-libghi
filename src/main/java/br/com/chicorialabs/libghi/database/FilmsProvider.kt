package br.com.chicorialabs.libghi.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class FilmsProvider : ContentProvider() {

    lateinit var mUriMatcher: UriMatcher
    lateinit var dbHelper: FilmsDataBaseHelper

    override fun onCreate(): Boolean {

        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher.addURI(AUTHORITY, "films", FILMS)
        mUriMatcher.addURI(AUTHORITY, "films/#", FILMS_BY_ID)

        context?.let {
            dbHelper = FilmsDataBaseHelper(it)
        }

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {

        const val AUTHORITY = "br.com.chicorialabs.libghi.provider"
        val BASE_URI = Uri.parse("content://$AUTHORITY")
        val URI_FILMS = Uri.withAppendedPath(BASE_URI, "films") //tabela onde os filmes serão gravados

        const val FILMS: Int = 1
        const val FILMS_BY_ID: Int = 2

    }
}