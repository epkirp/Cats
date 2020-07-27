package com.example.cats.app.ui.images

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.realm_queries.CatImageGateway
import com.example.cats.app.retrofit.RetrofitApi.getApi
import com.example.converter.RealmConverter
import com.example.gateways_retrofit.RetrofitCatImagesGateway
import com.example.model.CatImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

@InjectViewState
class ImagesPresenter : MvpPresenter<ImagesView>() {

    private val compositeDisposable = CompositeDisposable()
    private val catImagesGateway = RetrofitCatImagesGateway(getApi())
    private val converter = RealmConverter()

    private var isFirstLoad = true

    private var images = ArrayList<CatImage>()

    lateinit var realm: Realm
    var catImageGateway = CatImageGateway()

    var hasInternetConnection: Boolean = false


    override fun onFirstViewAttach() {
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
        if (hasInternetConnection) {
            loadImagesWithInternet()
        } else {
            loadImagesWithoutInternet()
        }
    }

    private fun loadImagesWithInternet() {
        catImagesGateway.getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                onResponse(response)
            }, {
                onFailure()
            })
            .let(compositeDisposable::add)
    }

    private fun onFailure() {
        viewState.changeRefreshVisibilityState(false)
        viewState.makeToastNoInternet()
        if (isFirstLoad) {
            isFirstLoad = false
        }
    }

    private fun onResponse(response: List<CatImage>?) {
        if (response != null) {
            catImageGateway.addAllCatsImages(realm, converter.imageCatToRealmList(response))

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

        val itemsFromRealm = converter.imageCatFromRealmList(catImageGateway.getAllCatImages(realm))
        if (itemsFromRealm != null) {
            images.clear()
            images.addAll(itemsFromRealm)
        }
        if (isFirstLoad) {
            viewState.initRecyclerView(images)
            isFirstLoad = false
        } else {
            viewState.updateAdapter()
        }
    }
}
