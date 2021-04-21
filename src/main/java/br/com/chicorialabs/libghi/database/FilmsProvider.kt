package br.com.chicorialabs.libghi.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.provider.BaseColumns._ID
import br.com.chicorialabs.libghi.database.FilmsDataBaseHelper.Companion.TABLE_FILMS

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

        return when(mUriMatcher.match(uri)){
            FILMS -> {
                fetchAllFilms(projection, selection, selectionArgs, sortOrder, uri)
            }
            FILMS_BY_ID -> {
                fetchFilmById(projection, uri, sortOrder)
            }
            else -> {throw UnsupportedSchemeException("")}
        }
    }

    private fun fetchAllFilms(
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
        uri: Uri
    ): Cursor? {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val cursor = db.query(
            TABLE_FILMS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    private fun fetchFilmById(
        projection: Array<out String>?,
        uri: Uri,
        sortOrder: String?
    ): Cursor? {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val cursor = db.query(
            TABLE_FILMS,
            projection,
            "$_ID = ?",
            arrayOf(uri.lastPathSegment),
            null,
            null,
            sortOrder
        )
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedSchemeException("Operação não suportada nessa URI")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if(mUriMatcher.match(uri) == FILMS) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val id: Long = db.insert(TABLE_FILMS, null, values)
            val insertUri: Uri? = Uri.withAppendedPath(BASE_URI, id.toString())
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return insertUri
        } else {
            throw UnsupportedSchemeException("Operação não suportada nessa Uri!")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {

        if (mUriMatcher.match(uri) == FILMS_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val linesAffected = db.delete(
                TABLE_FILMS,
                "$_ID = ?",
                arrayOf(uri.lastPathSegment)
            )
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linesAffected
        } else {
            throw UnsupportedSchemeException("Operação não suportada nessa URI")
        }

    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        if (mUriMatcher.match(uri) == FILMS_BY_ID) {
            val db : SQLiteDatabase = dbHelper.writableDatabase
            val linesAffected = db.update(
                TABLE_FILMS,
                values,
                "$_ID = ?",
                arrayOf(uri.lastPathSegment)
            )
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linesAffected
        } else {
            throw UnsupportedSchemeException("Operação não suportada nessa URI")
        }
    }

    companion object {

        const val AUTHORITY = "br.com.chicorialabs.libghi.provider"
        val BASE_URI = Uri.parse("content://$AUTHORITY")
        val URI_FILMS =
            Uri.withAppendedPath(BASE_URI, "films") //tabela onde os filmes serão gravados

        const val FILMS: Int = 1
        const val FILMS_BY_ID: Int = 2

    }
}