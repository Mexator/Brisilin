package com.mexator.fintech_test.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.mexator.fintech_test.R
import com.mexator.fintech_test.databinding.ActivityMainBinding
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var viewModelDisposable: Disposable? = null

    private val tabSelectedListener =
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.onTabSelected(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelDisposable = viewModel.viewState.subscribe { state ->
            if (binding.tabs.tabCount != state.displayedSources.size) {
                setupTabs(binding.tabs, tabSelectedListener, state)
            }

            val navController = findNavController(R.id.navHostFragment)
            val bundle = Bundle().also {
                it.putParcelable(
                    "source",
                    state.displayedSources[state.chosenSource]
                )
            }
            navController.navigate(R.id.feedFragment, bundle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelDisposable?.dispose()
    }

    private fun setupTabs(
        tabLayout: TabLayout,
        listener: TabLayout.OnTabSelectedListener,
        state: MainViewState
    ) {
        tabLayout.removeOnTabSelectedListener(listener)
        tabLayout.removeAllTabs()
        for (source in state.displayedSources) {
            tabLayout.addTab(tabLayout.newTab().setText(source.name))
        }
        tabLayout.addOnTabSelectedListener(listener)
        tabLayout.getTabAt(state.chosenSource)?.select()
    }
}