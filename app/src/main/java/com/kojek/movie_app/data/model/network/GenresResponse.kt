package com.kojek.movie_app.data.model.network

import com.kojek.movie_app.data.model.entity.Genre
import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    override var results: List<Genre>
) : BaseListResponse<Genre>
