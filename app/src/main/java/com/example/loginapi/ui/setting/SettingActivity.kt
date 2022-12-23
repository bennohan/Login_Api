package com.example.loginapi.ui.setting

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import android.os.Bundle
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.openActivity
import com.example.loginapi.R
import com.example.loginapi.base.activity.BaseActivity
import com.example.loginapi.data.constant.Cons
import com.example.loginapi.databinding.ActivitySettingBinding
import com.example.loginapi.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding,SettingViewModel>(R.layout.activity_setting){

    @Inject
    lateinit var session: CoreSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        binding.hasBiometric = hasBiometricCapability()
        binding.enableBiometric = session.getBoolean(Cons.BIOMETRIC)

        binding.btnBiometric.setOnCheckedChangeListener { buttonView, isChecked ->
            session.setValue(Cons.BIOMETRIC, isChecked)

            binding.btnLogout.setOnClickListener {
                viewModel.logout{
                    openActivity<LoginActivity>()
                    finish()
                }
            }
        }

    }
    private fun hasBiometricCapability():Boolean {
        val biometricManager = BiometricManager.from(this)
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }
}
