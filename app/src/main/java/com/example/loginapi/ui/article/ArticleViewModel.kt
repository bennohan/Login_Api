package com.example.loginapi.ui.article

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.*
import com.crocodic.core.extension.toList
import com.example.loginapi.api.ApiService
import com.example.loginapi.base.viewmodel.BaseVewModel
import com.example.loginapi.data.Article
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val apiService: ApiService, private val gson: Gson): BaseVewModel() {
    fun listArticle(page: Int) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver({ apiService.listArticle(page)}, false, object : ApiObserver.ResponseListener {
            override suspend fun onSuccess(response: JSONObject) {
                val status = response.getInt("api_status")
                if (status == 1) {
                    val data = response.getJSONObject(ApiCode.DATA).getJSONArray("article").toList<Article>(gson)
                    val article = DataObserver(page, data)
                    _apiResponse.send(ApiResponse(status = ApiStatus.SUCCESS, data = article))
                } else {
                    val message = response.getString(ApiCode.MESSAGE)
                    _apiResponse.send(ApiResponse(status = ApiStatus.ERROR, message = message))
                }
            }
        })
    }
}