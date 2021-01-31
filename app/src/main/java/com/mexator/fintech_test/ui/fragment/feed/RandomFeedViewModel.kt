package com.mexator.fintech_test.ui.fragment.feed

import com.mexator.fintech_test.data.Source

class RandomFeedViewModel(override val source: Source.Random) : FeedViewModel() {
    init {
        getNextPost()
    }

    override fun onPageSelected(pageNumber: Int) {
        getNextPost()
    }

    private fun getNextPost() {
        with(_viewState) {
            onNext(value!!.copy(progress = true))
            repository.getRandom().subscribe({ post ->
                val state = value!!
                onNext(
                    FeedViewState(
                        progress = false,
                        error = false,
                        loadedPosts = state.loadedPosts + post,
                        nextPageToLoad = state.nextPageToLoad + 1
                    )
                )
            }) {
                onNext(
                    value!!.copy(
                        progress = false,
                        error = true
                    )
                )
            }
        }
    }
}