package com.example.loginapi.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.extension.hideSoftKeyboard
import com.crocodic.core.extension.openActivity
import com.example.loginapi.R
import com.example.loginapi.ui.setting.SettingActivity
import com.example.loginapi.base.activity.BaseActivity
import com.example.loginapi.data.user.User
import com.example.loginapi.data.user.UserDao
import com.example.loginapi.databinding.ActivityHomeBinding
import com.example.loginapi.databinding.ItemFriendBinding
import com.example.loginapi.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel> (R.layout.activity_home) {

    @Inject
    lateinit var userDao: UserDao

    private var friend = ArrayList<User>()

    private val adapter by lazy {
        ReactiveListAdapter<ItemFriendBinding, User>(R.layout.item_friend)
    }

    private val runnable by lazy {
        Runnable {
            refreshData()
        }
    }

    private var filter: String? = null

    private val handler = Handler(Looper.getMainLooper())

    private var keyword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvFriend.adapter = adapter

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

        binding.etSearch.doAfterTextChanged {
            keyword = if (it.toString().trim().isEmpty()) {
                null
            }else {
                it.toString()
            }
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 1500)
        }

        binding.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                filter = if (p2 == 0) {
                    null
                } else {
                    binding.spFilter.selectedItem as String
                }
                refreshData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                filter = null
            }

        }

        binding.cbSort.setOnCheckedChangeListener { compoundButton, b ->
            if(b) {
                friend.sortByDescending { it?. likes }
            }else{
                friend.sortBy { it?. id }
            }
            binding.rvFriend.adapter?.notifyItemChanged(0,friend.size)
        }

        binding.etSearch.setOnEditorActionListener{
            textView, i , keyEvent -> textView.hideSoftKeyboard()
            true
        }


        refreshData()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.friends.collect {
                    adapter.submitList(it)
                }
            }
        }


    }

    private fun refreshData() {
        viewModel.getFriends(keyword, filter )
    }

    }
