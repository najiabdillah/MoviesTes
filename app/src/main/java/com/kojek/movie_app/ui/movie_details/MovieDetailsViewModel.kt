package com.kojek.movie_app.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kojek.movie_app.data.db.repository.MovieRepository
import com.kojek.movie_app.data.model.Event
import com.kojek.movie_app.data.model.GoToCast
import com.kojek.movie_app.data.model.GoToVideo
import com.kojek.movie_app.data.model.entity.Cast
import com.kojek.movie_app.data.model.entity.Movie
import com.kojek.movie_app.data.model.entity.Video
import com.kojek.movie_app.ui.BaseViewModel
import com.kojek.movie_app.util.extension.liveDataBlockScope

class MovieDetailsViewModelFactory(private val movieId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieId) as T
    }
}

class MovieDetailsViewModel(movieId: Int) : BaseViewModel(), GoToCast, GoToVideo {

    private val movieRepository = MovieRepository()
    val movie: LiveData<Movie>
    val videoList: LiveData<List<Video>>
    val castList: LiveData<List<Cast>>

    override val goToCastDetailsEvent: MutableLiveData<Event<Cast>> = MutableLiveData()
    override val goToVideoEvent: MutableLiveData<Event<Video>> = MutableLiveData()

    init {
        movie = liveDataBlockScope {
            movieRepository.loadDetails(movieId) { mSnackBarText.postValue(Event(it)) }
        }

        videoList = liveDataBlockScope {
            movieRepository.loadVideos(movieId) { mSnackBarText.postValue(Event(it)) }
        }

        castList = liveDataBlockScope {
            movieRepository.loadCredits(movieId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}
