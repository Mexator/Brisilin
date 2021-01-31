package com.mexator.fintech_test.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mexator.fintech_test.data.Source
import com.mexator.fintech_test.ui.fragment.feed.FeedViewModel
import com.mexator.fintech_test.ui.fragment.feed.PagedFeedViewModel
import com.mexator.fintech_test.ui.fragment.feed.RandomFeedViewModel

class FeedViewModelFactory(private val source: Source) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == FeedViewModel::class.java) {
            if (source is Source.PagedSource)
                return PagedFeedViewModel(source) as T
            if (source is Source.Random)
                return RandomFeedViewModel(source) as T
        }

        return super.create(modelClass)
    }
}