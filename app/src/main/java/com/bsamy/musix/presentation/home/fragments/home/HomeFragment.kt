package com.bsamy.musix.presentation.home.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bsamy.musix.R
import com.bsamy.musix.base.BaseFragment
import com.bsamy.musix.base.ResultModel
import com.bsamy.musix.base.getErrorMessage
import com.bsamy.musix.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewBinder: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onBindFinished(savedInstanceState: Bundle?) {
        listenForInputs()
        observeResult()
    }

    private fun listenForInputs() {
        binding.musicListRecycler.onMusicItemClickedListener = { model, _ ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMusicDetailsFragment(model)
            )
        }

        binding.searchEt.doOnTextChanged { text, _, _, _ ->
            viewModel.userSearch(text?.toString())
        }
    }

    private fun observeResult() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.musicSearchResult.collect { result ->
                    when (result) {
                        is ResultModel.Progress -> showProgress(show = true)
                        is ResultModel.ErrorResult -> {
                            showProgress(show = false)
                            showMessage(result.getErrorMessage(requireContext()))
                        }
                        is ResultModel.SuccessResult -> {
                            showProgress(show = false)
                            val items = result.data
                            if (items.isEmpty()) showMessage(getString(R.string.not_found_error))
                            else binding.musicListRecycler.submit(items)
                        }
                        else -> return@collect
                    }
                }
            }
        }
    }

    private fun showProgress(show: Boolean) {
        binding.progressView.isVisible = show
    }

    private fun showMessage(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()

    override val viewModel: HomeViewModel by viewModels()

    companion object {

        private const val TAG = "HomeFragment"

        fun instance() = HomeFragment()
    }
}