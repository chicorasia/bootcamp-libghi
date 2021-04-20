package br.com.chicorialabs.libghi.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class FilmsDataBaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    "databaseFilms",
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {

            val sql = "CREATE TABLE $TABLE_FILMS (" +
                    "$_ID INTEGER NOT NULL PRIMARY KEY, +" +
                    "$TITLE TEXT NOT NULL," +
                    "$ORIGINAL_TITLE TEXT NOT NULL, +" +
                    "$ORIGINAL_TITLE_ROMANISED TEXT NOT NULL," +
                    "$DIRECTOR TEXT NOT NULL," +
                    "$RELEASE_DATE INTEGER NOT NULL," +
                    "$RUNNING_TIME INTEGER NOT NULL," +
                    "$IS_WATCHED INTEGER DEFAULT 0," +
                    "$RATING INTEGER NOT DEFAULT 0" +
                    ")"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {

        const val TABLE_FILMS = "Films"
        const val TITLE = "title"
        const val ORIGINAL_TITLE ="original_title"
        const val ORIGINAL_TITLE_ROMANISED = "original_title_romanised"
        const val DIRECTOR = "director"
        const val RELEASE_DATE = "release_date"
        const val RUNNING_TIME = "running_time"
        const val IS_WATCHED = "is_watched"
        const val RATING = "rating"
    }
}