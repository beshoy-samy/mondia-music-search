package com.bsamy.musix.presentation.home.fragments.home

import androidx.lifecycle.viewModelScope
import com.bsamy.musix.base.BaseViewModel
import com.bsamy.musix.base.ResultModel
import com.bsamy.musix.domain.models.music.MusicDomainModel
import com.bsamy.musix.domain.usecases.auth.AuthenticationUseCase
import com.bsamy.musix.domain.usecases.auth.authenticationUseCase
import com.bsamy.musix.domain.usecases.music.MusicSearchUseCase
import com.bsamy.musix.domain.usecases.music.musicSearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val musicUseCase: MusicSearchUseCase = musicSearchUseCase,
    private val authenticatorUseCase: AuthenticationUseCase = authenticationUseCase,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : BaseViewModel() {

    private val _musicSearchResult =
        MutableStateFlow<ResultModel<List<MusicDomainModel>>>(ResultModel.Idle)
    val musicSearchResult: StateFlow<ResultModel<List<MusicDomainModel>>>
        get() = _musicSearchResult

    @FlowPreview
    fun fetch(query: String) {
        viewModelScope.launch(coroutineContext) {
            authenticatorUseCase.authenticate()
                .onStart { _musicSearchResult.emit(ResultModel.Progress) }
                .flatMapMerge { token -> musicUseCase.searchForMusic(query, token) }
                .catch { _musicSearchResult.emit(ResultModel.ErrorResult(it)) }
                .collect { _musicSearchResult.emit(ResultModel.SuccessResult(it)) }
        }
    }
}