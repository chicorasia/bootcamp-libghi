package br.com.chicorialabs.libghi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.libghi.R
import br.com.chicorialabs.libghi.databinding.ListaFilmeItemBinding
import br.com.chicorialabs.libghi.model.Film

class FilmAdapter(private val filmList: List<Film>) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder =
        ListaFilmeItemBinding.inflate(LayoutInflater.from(parent.context)).let {
            FilmViewHolder(it)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        with (filmList[position]) {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int = filmList.size


}

class FilmViewHolder(val binding: ListaFilmeItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(film: Film) {
        binding.apply {
            cardFilmTitle.text = film.title
            cardFilmOriginalTitle.text = film.original_title
            cardFilmDirector.text = film.director
            cardFilmYear.text = film.release_date
            cardFilmRunningtime.text = film.runnning_time
        }
    }

}
