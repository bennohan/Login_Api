package com.example.loginapi.ui.article

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.api.DataObserver
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.base.adapter.CoreListAdapter.Companion.get
import com.crocodic.core.extension.initLoadMore
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.crocodic.core.helper.list.EndlessScrollListener
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.loginapi.ArticleDetailActivity
import com.example.loginapi.R
import com.example.loginapi.base.activity.BaseActivity
import com.example.loginapi.data.Article
import com.example.loginapi.data.constant.Cons
import com.example.loginapi.databinding.ActivityArticleBinding
import com.example.loginapi.databinding.ItemArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding,ArticleViewModel> (R.layout.activity_article) {

    private var article = ArrayList<Article?>()
    private lateinit var scrollListener: EndlessScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvArticle.adapter = CoreListAdapter<ItemArticleBinding,Article>(R.layout.item_article)
            .initItem(article) {position, data ->
                tos(data?.title ?: return@initItem)

                openActivity<ArticleDetailActivity>{
                    putExtra(Cons.BUNDLE.ARTICLE, data)
                }
            }



        scrollListener = binding.rvArticle.initLoadMore { page ->
            getData(page + 1)
            binding.rvArticle.adapter?.get()?.addNull()
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.apiResponse.collect{
                        when(it.status){
                            ApiStatus.SUCCESS -> {
                                val data = it.dataAs<DataObserver<Article>>()
                                val datas = data?.datas

                                binding.rvArticle.adapter?.get()?.removeNull()

                                if (data?.page == 1) {
                                    article.clear()
                                    initSlider(datas as List<Article>)
                                    binding.rvArticle.adapter?.notifyDataSetChanged()
                                    scrollListener?.resetState()

                                }

                                if (datas?.isNotEmpty() == true) {

                                    if (binding.rvArticle.adapter?.itemCount == 0) {
                                        article.addAll(datas)
                                        binding.rvArticle.adapter?.notifyItemInserted(0)
                                    } else {
                                        article.addAll(datas)
                                        binding.rvArticle.adapter?.notifyItemInserted((binding.rvArticle.adapter?.itemCount ?: 1) - 1)
                                    }
                                }

                            }
                            else ->{

                            }
                        }
                    }
                }
            }



        }
        getData()

}

    private fun initSlider(data: List<Article>) {
        val imageList = ArrayList<SlideModel>()
        data.forEach{
            imageList.add(SlideModel(it.image,it.title))
        }
        binding.ivSlider.setImageList(imageList,ScaleTypes.CENTER_CROP)
    }

    private fun getData(page: Int = 1) {
        viewModel.listArticle(page)
    }

}