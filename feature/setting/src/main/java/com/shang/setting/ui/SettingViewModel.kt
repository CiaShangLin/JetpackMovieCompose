package com.shang.setting.ui

import androidx.lifecycle.ViewModel
import com.shang.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel()
