package com.mexator.fintech_test.ui.fragment.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.mexator.fintech_test.data.Source
import com.mexator.fintech_test.databinding.FragmentFeedBinding
import com.mexator.fintech_test.ui.base.FeedViewModelFactory
import com.mexator.fintech_test.ui.fragment.feed.list.FeedAdapter
import io.reactivex.disposables.Disposable

class FeedFragment : Fragment() {
    companion object {
        const val TAG = "FeedFragment"
    }

    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel
    private var viewModelDisposable: Disposable? = null
    private val adapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var source: Source = Source.Random(0)
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
        viewModel.getNextPage()
        viewModelDisposable = viewModel.viewState.subscribe(this::applyViewState)
        binding.postPager.adapter = adapter
        binding.postPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position % 5 == 3) {
                        viewModel.getNextPage()
                    }
                }
            }
        )

    }

    private fun applyViewState(state: FeedViewState) {
        Log.d("$TAG ${viewModel.source.name}", state.toString())
        adapter.submitList(state.loadedPosts)
    }
}