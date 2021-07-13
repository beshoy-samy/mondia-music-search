package com.bsamy.musix.presentation.home.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bsamy.musix.base.BaseEmptyFragment
import com.bsamy.musix.databinding.FragmentMusicDetailsBinding
import com.bsamy.musix.domain.models.music.MusicDomainModel
import com.bsamy.musix.utils.loadImage

class MusicDetailsFragment : BaseEmptyFragment<FragmentMusicDetailsBinding>() {

    override val viewBinder: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMusicDetailsBinding =
        FragmentMusicDetailsBinding::inflate

    val args: MusicDetailsFragmentArgs by navArgs()

    override fun onBindFinished(savedInstanceState: Bundle?) {
        setupToolbar()
        bindMusicItem(args.music)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindMusicItem(musicItem: MusicDomainModel) {
        binding.toolbar.title = musicItem.type.key
        musicItem.bigCover?.let { binding.coverIv.loadImage(url = it) }
        binding.generesTv.text = musicItem.genres.toString()
        binding.titleTv.text = musicItem.title
        binding.artistTv.text = musicItem.artist.name
        binding.dateTv.text = musicItem.publishingDate
    }

    companion object {

        fun instance() = MusicDetailsFragment()
    }
}