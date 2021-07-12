package com.bsamy.musix.presentation.components.music

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bsamy.musix.R
import com.bsamy.musix.databinding.ItemMusicListBinding
import com.bsamy.musix.domain.models.music.MusicDomainModel
import com.bsamy.musix.model.imageLoader
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicListAdapter(private val listener: (model: MusicDomainModel, position: Int) -> Unit) :
    ListAdapter<MusicDomainModel, MusicListAdapter.ViewHolder>(MusicListDiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    class ViewHolder private constructor(private val binding: ItemMusicListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            model: MusicDomainModel,
            listener: (model: MusicDomainModel, position: Int) -> Unit
        ) {
            model.cover?.let {
                binding.coverIv.setImageResource(R.drawable.ic_image_placeholder)
                loadImage(it, binding.coverIv)
            }
            binding.titleTv.text = model.title
            binding.artistTv.text = model.artist.name
            binding.dateTv.text = model.publishingDate
            binding.typeTv.text = model.type.key
            binding.root.setOnClickListener { listener.invoke(model, layoutPosition) }
        }

        private fun loadImage(imageUrl: String, coverIv: ShapeableImageView) =
            CoroutineScope(Dispatchers.IO).launch {
                imageLoader.loadImage(imageUrl)
                    .catch {
                        withContext(Dispatchers.Main) {
                            coverIv.setImageResource(R.drawable.ic_broken_image)
                        }
                        Log.e("ImageLoader", "", it)
                    }.collect { bitmap ->
                        withContext(Dispatchers.Main) { coverIv.setImageBitmap(bitmap) }
                    }
            }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMusicListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class MusicListDiffUtilsCallback : DiffUtil.ItemCallback<MusicDomainModel>() {

    override fun areItemsTheSame(old: MusicDomainModel, aNew: MusicDomainModel): Boolean {
        return old.id == aNew.id
    }

    override fun areContentsTheSame(
        old: MusicDomainModel,
        aNew: MusicDomainModel
    ): Boolean {
        return old == aNew
    }

}