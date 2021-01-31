package com.mexator.fintech_test.ui.activity

import com.mexator.fintech_test.data.Source
import com.mexator.fintech_test.ui.BaseViewModel

data class MainViewState(
    val displayedSources: List<Source> = emptyList(),
    val chosenSource: Int = 0
)

class MainViewModel : BaseViewModel<MainViewState>() {

    init {
        _viewState.onNext(
            MainViewState(
                displayedSources = listOf(
                    Source.Random(),
                    Source.PagedSource.Top(),
                    Source.PagedSource.Hot(),
                    Source.PagedSource.Latest()
                )
            )
        )
    }

    fun onTabSelected(position: Int) {
        with(_viewState) {
            onNext(
                value!!.copy(chosenSource = position)
            )
        }
    }
}
