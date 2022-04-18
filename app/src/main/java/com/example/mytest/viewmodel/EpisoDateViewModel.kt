package com.example.mytest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytest.model.MostPopularTvShowsResponse
import com.example.mytest.model.ShowDetailsResponse
import com.example.mytest.model.TvShow
import com.example.mytest.services.EpisoDateService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisoDateViewModel() : ViewModel() {

    var service = EpisoDateService.getInstance()

    private val _tvShowList : MutableLiveData<List<TvShow>> = MutableLiveData()

    val tvShowList : LiveData<List<TvShow>> = _tvShowList

    private val _error : MutableLiveData<String> = MutableLiveData()

    val error : LiveData<String> = _error

    private val _selected: MutableLiveData<TvShow> = MutableLiveData()

    val selected : LiveData<TvShow> = _selected

    private val _tvShow: MutableLiveData<TvShow> = MutableLiveData()

    val tvShowDetail : LiveData<TvShow> = _tvShow

    fun loadTvShows(){

        viewModelScope.launch {
            service.getMostPopularTvShows().enqueue(object : Callback<MostPopularTvShowsResponse> {
                override fun onResponse(
                    call: Call<MostPopularTvShowsResponse>,
                    response: Response<MostPopularTvShowsResponse>
                ) {
                    _tvShowList.postValue(response.body()!!.tvShows)
                }

                override fun onFailure(call: Call<MostPopularTvShowsResponse>, t: Throwable) {
                    _error.postValue(t.message)
                }
            })
        }
    }

    fun setSelectedItem(tvShow: TvShow) {
        _selected.value = tvShow
    }

//    fun loadDetail(value: TvShow?) {
//        CoroutineScope(Dispatchers.IO).launch {
//            service.getShowDetails(selected.value!!.id).enqueue(object :
//                Callback<ShowDetailsResponse> {
//                override fun onResponse(
//                    call: Call<ShowDetailsResponse>,
//                    response: Response<ShowDetailsResponse>
//                ) {
//                    _tvShow.postValue(response.body()!!.tvShow)
//                }
//
//                override fun onFailure(call: Call<ShowDetailsResponse>, t: Throwable) {
//                    _error.postValue(t.message)
//                }
//
//            })
//        }
//    }
}