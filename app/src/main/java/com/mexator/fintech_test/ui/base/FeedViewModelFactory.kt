package com.mexator.fintech_test.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mexator.fintech_test.data.Source
import com.mexator.fintech_test.ui.fragment.feed.FeedViewModel

class FeedViewModelFactory(private val source: Source) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == FeedViewModel::class.java) {
            FeedViewModel(source) as T
        } else super.create(modelClass)
    }
}