package com.mexator.fintech_test.ui.fragment.feed.list

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mexator.fintech_test.data.model.Post
import com.mexator.fintech_test.databinding.ItemPostBinding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers

class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        const val POSITION_IMAGE = 0
        const val POSITION_PROGRESS = 1
        const val POSITION_ERROR = 2
    }

    fun bind(post: Post) {
        binding.postText.text = post.description
        loadImage(post.gifURL)
        binding.retryButton.setOnClickListener { loadImage(post.gifURL) }
    }

    private fun loadImage(address: String) {
        binding.flipper.displayedChild = POSITION_PROGRESS
        Glide.with(binding.root.context)
            .load(address)
            .addListener(
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Completable.fromRunnable {
                            binding.flipper.displayedChild = POSITION_ERROR
                        }
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Completable.fromRunnable {
                            binding.flipper.displayedChild = POSITION_IMAGE
                            Glide.with(binding.root.context).load(resource).into(binding.postImage)
                        }.subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                        return false
                    }

                }).submit()
    }
}

object PostDiff : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        // In context of this test task, I assume that items never change
        return true
    }
}

class FeedAdapter : ListAdapter<Post, PostViewHolder>(PostDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}