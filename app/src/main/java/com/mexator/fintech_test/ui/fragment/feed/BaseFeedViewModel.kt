package com.mexator.fintech_test.ui.fragment.feed

import com.mexator.fintech_test.data.Repository
import com.mexator.fintech_test.data.Source
import com.mexator.fintech_test.data.model.Post
import com.mexator.fintech_test.ui.BaseViewModel

data class FeedViewState(
    val progress: Boolean = false,
    val error: Boolean = false,
    val loadedPosts: List<Post> = emptyList(),
    val nextPageToLoad: Int = 0
)

abstract class FeedViewModel : BaseViewModel<FeedViewState>() {
    abstract val source: Source
    protected val repository = Repository

    init {
        _viewState.onNext(
            FeedViewState()
        )
    }

    abstract fun onPageSelected(pageNumber: Int)
}

