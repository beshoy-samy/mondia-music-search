package com.bsamy.musix.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseEmptyActivity<T : ViewBinding> : AppCompatActivity() {

    private lateinit var _binding: T
    protected val binding: T get() = _binding
    protected abstract val viewBinder: (LayoutInflater) -> T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = viewBinder.invoke(layoutInflater)
        setContentView(binding.root)
        onBindFinished(savedInstanceState)
    }

    abstract fun onBindFinished(savedInstanceState: Bundle?)

}

abstract class BaseActivity<T : ViewBinding, VM : BaseViewModel> : BaseEmptyActivity<T>() {

    protected abstract val viewModel: VM

}