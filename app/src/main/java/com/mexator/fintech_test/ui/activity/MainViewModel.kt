package com.mexator.fintech_test.ui.activity

import androidx.lifecycle.ViewModel
import com.mexator.fintech_test.data.Source
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

data class MainViewState(
    val displayedSources: List<Source> = emptyList(),
    val chosenSource: Int = 0
)

class MainViewModel : ViewModel() {
    private val _viewState = BehaviorSubject.create<MainViewState>()
    val viewState: Observable<MainViewState> = _viewState

    init {
        _viewState.onNext(
            MainViewState(
                displayedSources = listOf(
                    Source.Random(),
                    Source.Top(),
                    Source.Hot(),
                    Source.Latest()
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
