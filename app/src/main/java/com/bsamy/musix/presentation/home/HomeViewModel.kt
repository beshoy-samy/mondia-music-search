package com.bsamy.musix.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bsamy.musix.base.BaseViewModel
import com.bsamy.musix.model.Network
import com.bsamy.musix.model.RequestType
import com.bsamy.musix.model.dtos.AuthenticationDto
import com.bsamy.musix.model.network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val networkConnection: Network = network) : BaseViewModel() {

    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {

            val result =
                networkConnection.fetch(
                    "v0/api/gateway/token/client",
                    RequestType.POST,
                    mapOf("X-MM-GATEWAY-KEY" to "Ge6c853cf-5593-a196-efdb-e3fd7b881eca"),
                    returnType = AuthenticationDto::class.java
                )
            Log.d("Network Result", "$result")

        }
    }


}