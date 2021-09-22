package com.kojek.movie_app.data.model.network

interface BasePageListResponse<T> {
    var page: Int
    var results: List<T>
}