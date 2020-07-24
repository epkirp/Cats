package com.example.cats.app.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.cats.app.BaseView
import com.example.cats.domain.model.CatImage
import com.example.cats.gateway.retrofit.RetrofitApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

@InjectViewState
class BasePresenter : MvpPresenter<BaseView>() {

    private val compositeDisposable = CompositeDisposable()

    private var isFirstLoad = true
    private var images = ArrayList<CatImage>()

    lateinit var realm: Realm

    var catImageGateway = CatImageGateway()
    var workInternet: Boolean = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadImages()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun onRefresh() {
        viewState.changeRefreshVisibilityState(true)

        images.clear()
        loadImages()

        viewState.changeRefreshVisibilityState(false)
    }

    fun loadImages() {
        if (workInternet) {
            loadImagesWithInternet()
        } else {
            loadImagesWithoutInternet()
        }
    }

    private fun loadImagesWithInternet() {
        RetrofitApi.buildService().getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                onResponse(response)
            }, { t ->
                onFailure(t)
            })
            .let(compositeDisposable::add)
    }

    private fun onFailure(t: Throwable) {
       // images.clear()

        viewState.changeRefreshVisibilityState(false)
        viewState.makeToastNoInternet()
        if (isFirstLoad) {
            isFirstLoad = false
        }
    }

    private fun onResponse(response: List<CatImage>?) {

        if (response != null) {

            catImageGateway.addAllCatsImage(realm, response)

            images.addAll(response)
            if (isFirstLoad) {
                viewState.initRecyclerView(images)
                isFirstLoad = false
            } else {
                viewState.updateAdapter()
            }
        } else {
            images.clear()
        }
    }

    private fun loadImagesWithoutInternet() {

        val itemsFromRealm = catImageGateway.getAllCatImage(realm)
        if (itemsFromRealm != null) {
            images.clear()
            images.addAll(itemsFromRealm)
        }
    }
}
