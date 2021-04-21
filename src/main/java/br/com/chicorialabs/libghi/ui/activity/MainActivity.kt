package br.com.chicorialabs.libghi.ui.activity

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns._ID
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.libghi.FilmListViewModel
import br.com.chicorialabs.libghi.database.FilmsDataBaseHelper.Companion.DIRECTOR
import br.com.chicorialabs.libghi.database.FilmsDataBaseHelper.Companion.ORIGINAL_TITLE
import br.com.chicorialabs.libghi.database.FilmsDataBaseHelper.Companion.ORIGINAL_TITLE_ROMANISED
import br.com.chicorialabs.libghi.database.FilmsDataBaseHelper.Companion.RELEASE_DATE
import br.com.chicorialabs.libghi.database.FilmsDataBaseHelper.Companion.RUNNING_TIME
import br.com.chicorialabs.libghi.database.FilmsDataBaseHelper.Companion.TITLE
import br.com.chicorialabs.libghi.database.FilmsProvider.Companion.URI_FILMS
import br.com.chicorialabs.libghi.databinding.ActivityMainBinding
import br.com.chicorialabs.libghi.model.Film
import br.com.chicorialabs.libghi.ui.adapter.FilmAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val filmRecyclerView: RecyclerView by lazy {
        binding.mainFilmRecyclerview
    }

    private val progressBar: ProgressBar by lazy {
        binding.mainProgressbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mViewModel = ViewModelProvider.NewInstanceFactory()
            .create(FilmListViewModel::class.java)


        mViewModel.init()

        mViewModel.progressBar.observe(this) { showProgressBar ->
            if (showProgressBar) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }


        mViewModel.snackbar.observe(this) { snackbarText ->
            snackbarText?.let {
                Snackbar.make(binding.root, snackbarText, Snackbar.LENGTH_LONG).show()
                mViewModel.onSnackBarShown()
            }
        }


        mViewModel.filmList.observe(this, {
            val adapter = mViewModel.filmList.value?.let {
                updateDb(it)
                FilmAdapter(it)
            }
            filmRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            filmRecyclerView.adapter = adapter

        })

    }

    private fun updateDb(filmList: List<Film>, ) {
        val cursor = contentResolver.query(URI_FILMS, null, null, null, "$_ID")
        if (cursor?.count != filmList.size) {
            Toast.makeText(
                this,
                "DB desatualizada\nContagem na DB: " +
                        "${cursor?.count}\nContagem no repo: ${filmList.size}",
                Toast.LENGTH_LONG
            ).show()
            filmList.forEach { film ->
                val values = ContentValues().apply {
                    put(TITLE, film.title)
                    put(ORIGINAL_TITLE, film.original_title)
                    put(ORIGINAL_TITLE_ROMANISED, film.original_title_romanised)
                    put(DIRECTOR, film.director)
                    put(RELEASE_DATE, film.release_date)
                    put(RUNNING_TIME, film.running_time)
                }

                contentResolver.insert(URI_FILMS, values)

            }
        } else {
            Toast.makeText(
                this,
                "DB está sincronizada!\nContagem na DB: " +
                        "${cursor?.count}\nContagem no repo: ${filmList.size}",
                Toast.LENGTH_LONG
            ).show()
        }

    }


}