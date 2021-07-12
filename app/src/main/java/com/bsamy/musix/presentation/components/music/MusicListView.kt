package com.bsamy.musix.presentation.components.music

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsamy.musix.R
import com.bsamy.musix.domain.models.music.MusicDomainModel
import com.bsamy.musix.utils.recycler.VerticalSpaceItemDecoration

class MusicListView(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {

    var onMusicItemClickedListener: ((model: MusicDomainModel, position: Int) -> Unit)? = null

    private val musicListAdapter = MusicListAdapter(this::onMusicItemClicked)

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addItemDecoration(VerticalSpaceItemDecoration(context, R.dimen.margin_small))
        adapter = musicListAdapter
        clipChildren = false
        clipToPadding = false
    }

    private fun onMusicItemClicked(model: MusicDomainModel, position: Int) {
        onMusicItemClickedListener?.invoke(model, position)
    }

    fun submit(items: List<MusicDomainModel>) = musicListAdapter.submitList(items)

}