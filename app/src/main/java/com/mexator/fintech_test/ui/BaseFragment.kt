package com.mexator.fintech_test.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @param VS ViewState type
 */
abstract class BaseFragment<VS> : Fragment() {
    protected abstract val viewModel: BaseViewModel<VS>
    private var viewModelDisposable: Disposable? = null

    protected abstract fun applyViewState(state: VS)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelDisposable =
            viewModel.viewState
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::applyViewState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelDisposable?.dispose()
    }
}