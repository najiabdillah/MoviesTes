package com.kojek.movie_app.ui.tv_show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kojek.movie_app.data.db.repository.TvRepository
import com.kojek.movie_app.data.model.Event
import com.kojek.movie_app.data.model.GoToCast
import com.kojek.movie_app.data.model.GoToVideo
import com.kojek.movie_app.data.model.entity.Cast
import com.kojek.movie_app.data.model.entity.TvShowDetails
import com.kojek.movie_app.data.model.entity.Video
import com.kojek.movie_app.ui.BaseViewModel
import com.kojek.movie_app.util.extension.liveDataBlockScope

class TVShowDetailsViewModelFactory(private val tvShowId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TVShowDetailsViewModel(tvShowId) as T
    }
}

class TVShowDetailsViewModel(tvShowId: Int) : BaseViewModel(), GoToVideo, GoToCast {

    private val tvShowRepository = TvRepository()
    val tvShow: LiveData<TvShowDetails>
    val videoList: LiveData<List<Video>>
    val castList: LiveData<List<Cast>>

    override val goToVideoEvent: MutableLiveData<Event<Video>> = MutableLiveData()
    override val goToCastDetailsEvent: MutableLiveData<Event<Cast>> = MutableLiveData()

    init {
        tvShow = liveDataBlockScope {
            tvShowRepository.loadDetails(tvShowId) { mSnackBarText.postValue(Event(it)) }
        }

        videoList = liveDataBlockScope {
            tvShowRepository.loadVideos(tvShowId) { mSnackBarText.postValue(Event(it)) }
        }

        castList = liveDataBlockScope {
            tvShowRepository.loadCredits(tvShowId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}