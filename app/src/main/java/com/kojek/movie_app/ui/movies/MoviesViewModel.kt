package com.kojek.movie_app.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.kojek.movie_app.data.db.repository.MovieRepository
import com.kojek.movie_app.data.model.Event
import com.kojek.movie_app.data.model.GoToMovie
import com.kojek.movie_app.data.model.entity.Movie
import com.kojek.movie_app.ui.BaseViewModel
import com.kojek.movie_app.util.extension.appendList
import com.kojek.movie_app.util.extension.liveDataBlockScope


class MoviesViewModel : BaseViewModel(), GoToMovie {

    private val movieRepository = MovieRepository()
    private val loadedMovies: LiveData<List<Movie>>
    private val moviesPage = MutableLiveData<Int>().apply { value = 1 }

    override val goToMovieDetailsEvent: MutableLiveData<Event<Movie>> = MutableLiveData()

    val movieList = MediatorLiveData<MutableList<Movie>>()

    init {
        loadedMovies = moviesPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadDiscoverList(it) { mSnackBarText.postValue(Event(it)) }
            }
        }

        movieList.addSource(loadedMovies) { it?.let { list -> movieList.appendList(list) } }
    }

    fun loadMoreMovies() {
        moviesPage.value = moviesPage.value?.plus(1)
    }
}