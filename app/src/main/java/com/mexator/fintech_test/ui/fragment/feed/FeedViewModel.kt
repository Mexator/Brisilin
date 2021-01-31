package com.mexator.fintech_test.ui.fragment.feed

import androidx.lifecycle.ViewModel
import com.mexator.fintech_test.data.Repository
import com.mexator.fintech_test.data.Source
import com.mexator.fintech_test.data.model.Post
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

data class FeedViewState(
    val progress: Boolean = false,
    val error: Boolean = false,
    val loadedPosts: List<Post> = emptyList(),
    val position: Int = 0,
    val nextPageToLoad: Int = 0
)

class FeedViewModel(val source: Source) : ViewModel() {
    private val repository = Repository

    private val _viewState = BehaviorSubject.create<FeedViewState>()
    val viewState: Observable<FeedViewState> = _viewState

    init {
        _viewState.onNext(
            FeedViewState()
        )
    }

    fun getNextPage() {
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

