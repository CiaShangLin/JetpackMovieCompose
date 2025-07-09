package com.shang.setting.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.repository.UserDataRepository
import com.shang.model.LanguageMode
import com.shang.model.ThemeMode
import com.shang.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    val userData = userDataRepository.userData.stateIn(
        viewModelScope,
        initialValue = UserData.getDefault(),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataRepository.setThemeMode(themeMode)
        }
    }

    fun setLanguageMode(languageMode: LanguageMode) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataRepository.setLanguageMode(languageMode)
        }
    }
}
