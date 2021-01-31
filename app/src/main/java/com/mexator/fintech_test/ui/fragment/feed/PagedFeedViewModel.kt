package com.mexator.fintech_test.ui.fragment.feed

import com.mexator.fintech_test.data.Source

class PagedFeedViewModel(override val source: Source.PagedSource) : FeedViewModel() {
    companion object {
        const val PAGE_SIZE = 5
        const val PRELOAD_MARGIN = 2
    }

    init {
        getNextPage()
    }

    override fun onPageSelected(pageNumber: Int) {
        if (pageNumber % PAGE_SIZE == PAGE_SIZE - PRELOAD_MARGIN) {
            getNextPage()
        }
    }

    private fun getNextPage() {
        with(_viewState) {
            onNext(value!!.copy(progress = true))
            repository.getPosts(source, this.value!!.nextPageToLoad)
                .subscribe({ posts ->
                    val state = value!!
                    onNext(
                        FeedViewState(
                            progress = false,
                            error = false,
                            loadedPosts = state.loadedPosts + posts,
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