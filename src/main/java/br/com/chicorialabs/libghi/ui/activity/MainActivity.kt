package br.com.chicorialabs.libghi.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.libghi.FilmListViewModel
import br.com.chicorialabs.libghi.database.DataBaseSyncHelper
import br.com.chicorialabs.libghi.databinding.ActivityMainBinding
import br.com.chicorialabs.libghi.repository.FilmRepository
import br.com.chicorialabs.libghi.ui.adapter.FilmAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val mViewModel: FilmListViewModel by viewModel {
        parametersOf(FilmRepository())
    }

    private val filmRecyclerView: RecyclerView by lazy {
        binding.mainFilmRecyclerview
    }

    private val progressBar: ProgressBar by lazy {
        binding.mainProgressbar
    }

    private val dbSyncHelper: DataBaseSyncHelper by lazy {
        DataBaseSyncHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                dbSyncHelper.updateDb(it)
                FilmAdapter(it)
            }
            filmRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            filmRecyclerView.adapter = adapter

        })

    }



}