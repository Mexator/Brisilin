package com.mexator.fintech_test.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Source : Parcelable {
    abstract val path: String
    abstract val name: String

    @Parcelize
    class Hot(val page: Int = 0) : Source() {
        override val path = "hot"
        override val name = "Hot"
    }

    @Parcelize
    class Latest(val page: Int = 0) : Source() {
        override val path = "latest"
        override val name = "Latest"
    }

    @Parcelize
    class Top(val page: Int = 0) : Source() {
        override val path = "top"
        override val name = "Top"
    }

    @Parcelize
    class Random(val page: Int = 0) : Source() {
        override val path = "random"
        override val name = "Random"
    }
}