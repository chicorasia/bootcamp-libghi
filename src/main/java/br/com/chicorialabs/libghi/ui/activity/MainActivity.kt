package br.com.chicorialabs.libghi.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.libghi.FilmListViewModel
import br.com.chicorialabs.libghi.databinding.ActivityMainBinding
import br.com.chicorialabs.libghi.repository.FilmLoadError
import br.com.chicorialabs.libghi.ui.adapter.FilmAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.net.UnknownHostException

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

        initFilmList(mViewModel)

        mViewModel.snackbar.observe(this) {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                mViewModel.onSnackBarShown()
            }
        }

        mViewModel.progressBar.observe(this) { isShown ->
            if(isShown) showProgressBar() else (hideProgressBar())

        }


    }

    private fun initFilmList(mViewModel: FilmListViewModel) {
        lifecycleScope.launch {
            mViewModel.init()
            delay(5000)
            initAdapter(mViewModel)
        }
    }

    private fun initAdapter(mViewModel: FilmListViewModel) {
        val adapter = mViewModel.filmList.value?.let { FilmAdapter(it) }
        filmRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        filmRecyclerView.adapter = adapter
    }


    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }


}