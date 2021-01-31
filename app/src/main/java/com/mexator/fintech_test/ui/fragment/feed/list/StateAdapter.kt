package com.mexator.fintech_test.ui.fragment.feed.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mexator.fintech_test.R

/**
 * ViewHolders for different states (Loading, Error)
 */
sealed class StateHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class LoadingHolder(itemView: View) : StateHolder(itemView)
class ErrorHolder(itemView: View) : StateHolder(itemView)

/**
 * [StateAdapter] is an adapter class that can show loading and errors in [RecyclerView].
 * It is made as a wrapper around [ConcatAdapter] with [LoadingAdapter] and [ErrorAdapter]
 * inside it.
 * @property state when changed, adapter shows another state. If null, all cells are hidden
 */
class StateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class State { Loading, Error }

    var state: State? = null
        set(value) {
            when (value) {
                State.Error -> errorAdapter.shown = true
                State.Loading -> loadingAdapter.shown = true
                else -> {
                    errorAdapter.shown = false
                    loadingAdapter.shown = false
                }
            }
            field = value
            notifyDataSetChanged()
        }

    private var errorAdapter = ErrorAdapter()
    private var loadingAdapter = LoadingAdapter()
    private val adapter = ConcatAdapter(loadingAdapter, errorAdapter)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        adapter.onCreateViewHolder(parent, viewType)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        adapter.onBindViewHolder(holder, position)

    override fun getItemCount(): Int =
        adapter.itemCount

    override fun getItemViewType(position: Int): Int {
        return adapter.getItemViewType(position)
    }
}

class LoadingAdapter : RecyclerView.Adapter<LoadingHolder>() {
    var shown: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingHolder =
        LoadingHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_loading, parent, false)
        )

    override fun onBindViewHolder(holder: LoadingHolder, position: Int) {}

    override fun getItemCount(): Int {
        return if (shown) 1 else 0
    }

}

class ErrorAdapter : RecyclerView.Adapter<ErrorHolder>() {
    var shown: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorHolder =
        ErrorHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_error, parent, false)
        )

    override fun onBindViewHolder(holder: ErrorHolder, position: Int) {}

    override fun getItemCount(): Int {
        return if (shown) 1 else 0
    }
}