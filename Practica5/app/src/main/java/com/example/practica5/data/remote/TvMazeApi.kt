package com.example.practica5.data.remote

import com.example.practica5.data.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeApi{
    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<SearchResult>
}