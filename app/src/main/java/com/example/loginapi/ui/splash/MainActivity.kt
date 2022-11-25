package com.example.loginapi.ui.splash

import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.loginapi.R
import com.example.loginapi.base.activity.BaseActivity
import com.example.loginapi.databinding.ActivityMainBinding
import com.example.loginapi.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.splash {
            openActivity<LoginActivity>()
            finish()
        }
    }
}