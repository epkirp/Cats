package com.example.cats.app

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.cats.domain.model.CatImage

interface BaseView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateAdapter()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun initRecyclerView(items: ArrayList<CatImage>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeRefreshVisibilityState(state: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun makeToastNoInternet()
}