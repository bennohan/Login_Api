package com.example.loginapi.ui.login

import android.os.Bundle
import com.example.loginapi.R
import com.example.loginapi.base.activity.BaseActivity
import com.example.loginapi.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}