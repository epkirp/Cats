package com.example.cats.presenters

import androidx.lifecycle.LiveData
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.cats.BaseView
import com.example.cats.model.AppDatabase
import com.example.cats.model.Image
import com.example.cats.model.ImageRepository
import com.example.cats.retrofit.RetrofitApi

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class BasePresenter: MvpPresenter<BaseView>() {

    private var currentPage = 1
    private var isFirstLoad = true
    var loadMoreImages = true
    private var items = ArrayList<Image>()

    fun onRefresh() {
        viewState.changeRefreshVisibilityState(true)

        currentPage = 1
        loadMoreImages = true
        items.clear()
        loadImages()

        viewState.changeRefreshVisibilityState(false)
    }

    fun loadImages() {

        RetrofitApi.galleryApi.getImages(currentPage)
            .enqueue(object : Callback<List<Image>?> {

                override fun onFailure(call: Call<List<Image>?>, t: Throwable) {
                    onFailureLoad()
                }

                override fun onResponse(
                    call: Call<List<Image>?>,
                    response: Response<List<Image>?>
                ) {
                    onResponseLoad(response)
                }
            })
    }

    private fun onFailureLoad() {
        items.clear()

        viewState.changeRefreshVisibilityState(false)
        if (isFirstLoad) {
            isFirstLoad = false
        }
    }

    private fun onResponseLoad(response: Response<List<Image>?>) {
        val body = response.body()
        val headers = response.headers()

        if (response.isSuccessful && body != null && headers != null) {

            response.headers()["pagination-count"]?.toInt()?.let {
                if (currentPage * RetrofitApi.LIMIT_PAGE >= it)
                    loadMoreImages = false
            }

            items.addAll(body)
            if (isFirstLoad) {
                viewState.initRecyclerView(items)
                isFirstLoad = false
            } else {
                viewState.updateAdapter()
            }
            currentPage++

        } else {
            items.clear()
        }
    }
}
