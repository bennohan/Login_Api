package com.example.loginapi.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.loginapi.base.viewmodel.BaseVewModel
import com.example.loginapi.data.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userDao: UserDao): BaseVewModel() {
    fun splash(done: (Boolean) -> Unit) = viewModelScope.launch {
        delay(3000)
        done(userDao.isLogin())
    }
}