package com.mexator.fintech_test.ui.fragment.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mexator.fintech_test.databinding.FragmentGenericPostFeedBinding

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentGenericPostFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenericPostFeedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}