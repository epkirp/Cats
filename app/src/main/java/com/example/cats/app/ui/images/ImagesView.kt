package com.example.cats.app.ui.images

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.model.CatImage

interface ImagesView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateAdapter()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initRecyclerView(items: ArrayList<CatImage>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeRefreshVisibilityState(state: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun makeToastNoInternet()
}