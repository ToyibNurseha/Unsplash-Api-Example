package com.toyibnurseha.colearntest.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.toyibnurseha.colearntest.data.local.MyPhoto
import com.toyibnurseha.colearntest.databinding.ItemPhotoBinding


class SearchAdapter : PagingDataAdapter<MyPhoto, SearchAdapter.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val view = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null)
            holder.bind(currentItem)
    }


    inner class ViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: MyPhoto) {
            with(binding) {
                itemView.setOnClickListener {
                    listener?.let {
                        it(photo)
                    }
                }

                Glide.with(itemView.context)
                    .load(photo.urls.regular)
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
                    .into(ivImage)
            }
        }
    }

    private var listener: ((MyPhoto?) -> Unit)? = null

    fun setOnItemClickListener(clickListener: ((MyPhoto?) -> Unit)?) {
        listener = clickListener
    }

    object DiffCallback : DiffUtil.ItemCallback<MyPhoto>() {
        override fun areItemsTheSame(oldItem: MyPhoto, newItem: MyPhoto): Boolean =
            newItem.id == oldItem.id


        override fun areContentsTheSame(oldItem: MyPhoto, newItem: MyPhoto): Boolean =
            newItem == oldItem

    }
}