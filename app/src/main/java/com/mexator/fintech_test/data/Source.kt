package com.mexator.fintech_test.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Source : Parcelable {
    abstract val path: String

    @Parcelize
    class Hot(val page: Int) : Source() {
        override val path = "hot"
    }

    @Parcelize
    class Latest(val page: Int) : Source() {
        override val path = "latest"
    }

    @Parcelize
    class Top(val page: Int) : Source() {
        override val path = "top"
    }
}