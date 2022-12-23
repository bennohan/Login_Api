package com.example.loginapi.ui.setting

import androidx.lifecycle.viewModelScope
import com.example.loginapi.base.viewmodel.BaseVewModel
import com.example.loginapi.data.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val userDao: UserDao): BaseVewModel() {
    fun logout  (logout: ()-> Unit) = viewModelScope.launch {
        userDao.deleteAll()
        logout()
    }
}