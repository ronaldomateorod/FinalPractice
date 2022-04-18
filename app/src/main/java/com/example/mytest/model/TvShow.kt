package com.example.mytest.model

import com.google.gson.annotations.SerializedName

class TvShow {
    var id: Long = 0
    var name: String = ""

    @SerializedName("image_thumbnail_path")
    var image: String = ""

    @SerializedName("start_date")
    var startDate: String = ""

    var country: String = ""
    var network: String = ""
    var status: String = ""
    var description: String = ""
}

class MostPopularTvShowsResponse{
    var total: Int = 0
    var page: Int = 1
    var pages: Int = 1
    @SerializedName("tv_shows")
    var tvShows: List<TvShow>? = null
}

class ShowDetailsResponse {
    var tvShow : TvShow? = null
}