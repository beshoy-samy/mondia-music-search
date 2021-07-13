package com.bsamy.musix.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.bsamy.musix.base.BaseEmptyActivity
import com.bsamy.musix.databinding.ActivitySplashBinding
import com.bsamy.musix.presentation.home.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : BaseEmptyActivity<ActivitySplashBinding>() {

    override val viewBinder: (LayoutInflater) -> ActivitySplashBinding =
        ActivitySplashBinding::inflate

    override fun onBindFinished(savedInstanceState: Bundle?) {

        lifecycleScope.launch() {
            delay(2000)
            withContext(Dispatchers.Main) {
                MainActivity.start(this@SplashActivity)
                finish()
            }
        }
    }
}