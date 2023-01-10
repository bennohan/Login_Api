package com.example.loginapi.data

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.loginapi.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Article (
    @Expose
    @SerializedName("created_at")
    val createdAt: String?,
    @Expose
    @SerializedName("title")
    val title: String?,
    @Expose
    @SerializedName("content")
    val content: String?,
    @Expose
    @SerializedName("image")
    val image: String?,
    @Expose
    @SerializedName("resize_image")
    val resizeImage: String?
): Parcelable {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["image", "imageThumbnail"], requireAll = false)
        fun loadImage(imageView: ImageView, image: String?, imageThumbnail: String?) {
            image?.let{
                val thumbnail = Glide
                    .with(imageView.context)
                    .load(imageThumbnail)
                    .apply(RequestOptions.centerCropTransform())
            Glide
                .with(imageView.context)
                .load(image)
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.placeholder)
                .thumbnail(thumbnail)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView)


        }
    }
}
}