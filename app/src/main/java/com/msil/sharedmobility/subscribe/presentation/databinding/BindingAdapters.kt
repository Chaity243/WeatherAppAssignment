package com.msil.sharedmobility.subscribe.presentation.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * BindingAdapters.kt contains Databinding conversions and adapters
 */

//@Inject
//lateinit var mPicasso: Picasso;


@BindingConversion
fun setVisibility(state: Boolean): Int {
    return if (state) View.VISIBLE else View.GONE
}

@BindingAdapter("app:imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null) {
        Picasso.get().load(url).into(imageView)
//        mPicasso
//            .load(url)
//            .into(imageView)
    }
}

@BindingAdapter("app:imageUrl", "app:placeHolder")
fun loadImage(imageView: ImageView, url: String?, placeholder: Int) {
    if (url != null) {
        Picasso.get().load(url).placeholder(placeholder).into(imageView)
//        mPicasso
//            .load(url)
//            .placeholder(placeholder)
//            .into(imageView)

    }
}
