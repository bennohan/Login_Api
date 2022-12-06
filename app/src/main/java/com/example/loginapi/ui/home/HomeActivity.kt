package com.example.loginapi.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.loginapi.R
import com.example.loginapi.SettingActivity
import com.example.loginapi.base.activity.BaseActivity
import com.example.loginapi.data.user.UserDao
import com.example.loginapi.databinding.ActivityHomeBinding
import com.example.loginapi.ui.login.LoginActivity
import com.example.loginapi.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel> (R.layout.activity_home) {

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDao.getUser().observe(this) {
            binding.user = it
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout {
                openActivity<LoginActivity>()
                finish()
            }

        }

        binding.btnSeetting.setOnClickListener {
            openActivity<SettingActivity>() {
                finish()
            }
        }

    }

    }
