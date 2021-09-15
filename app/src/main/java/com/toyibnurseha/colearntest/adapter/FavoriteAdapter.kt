package com.toyibnurseha.colearntest.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.toyibnurseha.colearntest.data.local.MyPhoto
import com.toyibnurseha.colearntest.data.local.PhotoFavoriteModel
import com.toyibnurseha.colearntest.databinding.ItemPhotoBinding

class FavoriteAdapter :
    PagingDataAdapter<PhotoFavoriteModel, FavoriteAdapter.ViewHolder>(DiffCallback) {

    private var photos = ArrayList<PhotoFavoriteModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val view = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null)
            holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoFavoriteModel) {
            with(binding) {
                itemView.setOnClickListener {
                    listener?.let {
                        it(photo)
                    }
                }

                Glide.with(itemView.context)
                    .load(photo.urlThumb)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .diskCacheStrategy(
                        DiskCacheStrategy.NONE
                    ).skipMemoryCache(true)
                    .into(ivImage)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<PhotoFavoriteModel>() {
        override fun areItemsTheSame(
            oldItem: PhotoFavoriteModel,
            newItem: PhotoFavoriteModel
        ): Boolean =
            newItem.id == oldItem.id


        override fun areContentsTheSame(
            oldItem: PhotoFavoriteModel,
            newItem: PhotoFavoriteModel
        ): Boolean =
            newItem == oldItem

    }

    private var listener: ((PhotoFavoriteModel?) -> Unit)? = null

    fun setOnItemClickListener(clickListener: ((PhotoFavoriteModel?) -> Unit)?) {
        listener = clickListener
    }
}