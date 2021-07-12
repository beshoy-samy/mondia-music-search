package com.bsamy.musix.presentation.home.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bsamy.musix.R
import com.bsamy.musix.base.BaseFragment
import com.bsamy.musix.base.ResultModel
import com.bsamy.musix.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewBinder: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onBindFinished(savedInstanceState: Bundle?) {
        observeResult()
        binding.helloTv.setOnClickListener {
            viewModel.fetch("ab")
        }
    }

    private fun observeResult() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.musicSearchResult.collect { result ->
                    when (result) {
                        is ResultModel.Progress ->
                            Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT)
                                .show()
                        is ResultModel.ErrorResult ->
                            binding.helloTv.text = result.throwable?.stackTraceToString()
                        is ResultModel.SuccessResult ->
                            binding.helloTv.text = result.data.toString()
                        else -> return@collect
                    }
                }
            }
        }
    }

    override val viewModel: HomeViewModel by viewModels()

    companion object {

        fun instance() = HomeFragment()
    }
}