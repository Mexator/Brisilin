package com.mexator.fintech_test.ui.fragment.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mexator.fintech_test.data.Source
import com.mexator.fintech_test.databinding.FragmentGenericPostFeedBinding
import com.mexator.fintech_test.ui.base.FeedViewModelFactory

class FeedFragment : Fragment() {
    companion object {
        const val TAG = "FeedFragment"
    }

    private lateinit var binding: FragmentGenericPostFeedBinding
    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var source: Source = Source.Top(0)
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
        binding = FragmentGenericPostFeedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNextPage()
        viewModel.viewState.subscribe { Log.d(TAG, it.toString()) }
    }
}