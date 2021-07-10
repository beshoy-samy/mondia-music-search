package com.bsamy.musix.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.bsamy.musix.base.BaseActivity
import com.bsamy.musix.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, HomeViewModel>() {

    override val viewBinder: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun onBindFinished(savedInstanceState: Bundle?) {
        binding.helloTv.setOnClickListener { viewModel.fetch() }
    }

    override val viewModel: HomeViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}