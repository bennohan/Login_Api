package com.example.loginapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.base.activity.NoViewModelActivity
import com.example.loginapi.data.Article
import com.example.loginapi.data.constant.Cons
//import com.example.loginapi.data.constant.Cons
import com.example.loginapi.databinding.ActivityArticleDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity :  NoViewModelActivity<ActivityArticleDetailBinding>(R.layout.activity_article_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val article : Article? = intent.getParcelableExtra(Cons.BUNDLE.ARTICLE)

        binding.data = article
    }
}