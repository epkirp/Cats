package com.example.cats

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.cats.model.Image

interface BaseView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateAdapter()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initRecyclerView(items: ArrayList<Image>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeRefreshVisibilityState(state: Boolean)
}