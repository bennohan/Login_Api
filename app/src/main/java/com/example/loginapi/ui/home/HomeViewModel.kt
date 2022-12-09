package com.example.loginapi.ui.home

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.extension.toList
import com.example.loginapi.api.ApiService
import com.example.loginapi.base.viewmodel.BaseVewModel
import com.example.loginapi.data.user.User
import com.example.loginapi.data.user.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService, private val userDao: UserDao, private val gson: Gson): BaseVewModel() {

    private val _friends = Channel<List<User>>()
    val friends = _friends.receiveAsFlow()

    fun logout(logout: () -> Unit) = viewModelScope.launch {
        userDao.deleteAll()
        logout()
    }

    fun getFriends(search: String? =null, filter:String?) = viewModelScope.launch {
        val idUser = userDao.userLogin().id
        ApiObserver({ apiService.getFriends(idUser, search, filter)},false, object : ApiObserver.ResponseListener{
            override suspend fun onSuccess(response: JSONObject) {
                val status = response.getInt(ApiCode.STATUS)
                if (status == ApiCode.SUCCESS){

                    val friendsArray = response.getJSONArray(ApiCode.DATA)
                    val _friendsList = friendsArray.toList<User>(gson)

                    val friendsList = if (search.isNullOrEmpty()) {
                        _friendsList
                    } else {
                        _friendsList.filter { it.name?.contains(search) == true }
                    }

                    _friends.send(friendsList)

                } else {
                    val message = response.getString(ApiCode.MESSAGE)
                }
            }
        })
    }

}