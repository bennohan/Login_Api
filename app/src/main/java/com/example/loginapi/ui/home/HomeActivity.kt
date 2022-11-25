package com.example.loginapi.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginapi.R
import com.example.loginapi.base.activity.BaseActivity
import com.example.loginapi.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel> (R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}