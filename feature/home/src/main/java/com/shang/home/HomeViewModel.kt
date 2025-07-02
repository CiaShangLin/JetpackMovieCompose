package com.shang.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.common.UiState
import com.shang.data.repository.UserDataRepository
import com.shang.domain.usecase.GetConfigurationUseCase
import com.shang.model.ConfigurationBean
import com.shang.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getConfigurationUseCase: GetConfigurationUseCase,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    // {"id":28,"name":"Action"}

    val configuration: StateFlow<UiState<ConfigurationBean>> = getConfigurationUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading,
        )

    val userData = userDataRepository.userData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserData(),
    )

    fun setConfiguration(configuration: ConfigurationBean) {
        viewModelScope.launch {
            userDataRepository.setConfiguration(configuration)
        }
    }
}
