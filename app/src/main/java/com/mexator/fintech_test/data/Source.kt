package com.mexator.fintech_test.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Source : Parcelable {
    abstract val path: String
    abstract val name: String

    @Parcelize
    class Random : Source() {
        override val path = "random"
        override val name = "Random"
    }

    sealed class PagedSource : Source() {
        @Parcelize
        class Hot : PagedSource() {
            override val path = "hot"
            override val name = "Hot"
        }

        @Parcelize
        class Latest : PagedSource() {
            override val path = "latest"
            override val name = "Latest"
        }

        @Parcelize
        class Top : PagedSource() {
            override val path = "top"
            override val name = "Top"
        }
    }
}

