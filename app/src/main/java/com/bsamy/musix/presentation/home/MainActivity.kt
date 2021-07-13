package com.bsamy.musix.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.bsamy.musix.base.BaseEmptyActivity
import com.bsamy.musix.databinding.ActivityMainBinding

class MainActivity : BaseEmptyActivity<ActivityMainBinding>() {

    override val viewBinder: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun onBindFinished(savedInstanceState: Bundle?) {

    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}