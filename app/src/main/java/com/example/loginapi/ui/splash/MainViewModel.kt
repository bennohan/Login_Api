package com.example.loginapi.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.loginapi.base.viewmodel.BaseVewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseVewModel() {
    fun splash(done: () -> Unit) = viewModelScope.launch {
        delay(3000)
        done()
    }
}