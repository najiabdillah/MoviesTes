package com.kojek.movie_app.data.model.network

interface BaseListResponse<T> {
    var results: List<T>
}
