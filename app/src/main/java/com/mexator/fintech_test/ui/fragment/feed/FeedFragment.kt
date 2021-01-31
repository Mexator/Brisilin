package com.mexator.fintech_test.ui.fragment.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.viewpager2.widget.ViewPager2
import com.mexator.fintech_test.data.Source
import com.mexator.fintech_test.databinding.FragmentFeedBinding
import com.mexator.fintech_test.ui.BaseFragment
import com.mexator.fintech_test.mvvm.FeedViewModelFactory
import com.mexator.fintech_test.ui.fragment.feed.list.FeedAdapter
import com.mexator.fintech_test.ui.fragment.feed.list.StateAdapter

class FeedFragment : BaseFragment<FeedViewState>() {
    companion object {
        const val TAG = "FeedFragment"
    }

    private lateinit var binding: FragmentFeedBinding
    override lateinit var viewModel: FeedViewModel
    private val postAdapter = FeedAdapter()
    private val stateAdapter = StateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var source: Source = Source.Random()
        arguments?.let { args ->
            args["source"]?.let {
                source = it as Source
            }
        }
        viewModel = FeedViewModelFactory(source).create(FeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postPager.adapter = ConcatAdapter(postAdapter, stateAdapter)
        binding.postPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.onPageSelected(position)
                }
            }
        )
    }

    override fun applyViewState(state: FeedViewState) {
        Log.d("$TAG ${viewModel.source.name}", state.toString())
        postAdapter.submitList(state.loadedPosts)
        if (state.error) {
            stateAdapter.state = StateAdapter.State.Error
        }
        if (state.progress) {
            stateAdapter.state = StateAdapter.State.Loading
        }
    }
}