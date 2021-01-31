package com.mexator.fintech_test.ui

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * @param VS ViewState type
 */
abstract class BaseViewModel<VS> : ViewModel() {
    protected val _viewState = BehaviorSubject.create<VS>()
    val viewState: Observable<VS> = _viewState
}