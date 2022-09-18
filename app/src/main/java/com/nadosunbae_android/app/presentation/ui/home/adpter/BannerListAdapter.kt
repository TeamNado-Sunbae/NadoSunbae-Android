package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity


class BannerListAdapter(private val context: Context, private val sliderImage: List<String>) :
    RecyclerView.Adapter<BannerListAdapter.BannerListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerListHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_banner, parent, false)
        return BannerListHolder(view)
    }

    override fun onBindViewHolder(holder: BannerListHolder, position: Int) {
        //holder.bindSliderImage(sliderImage[position])
        holder.bindSliderImage(sliderImage[position % sliderImage.size])

    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    inner class BannerListHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val mImageView: ImageView
        fun bindSliderImage(imageURL: String?) {
            val into = Glide.with(context)
                .load(imageURL)
                .into(mImageView)
        }

        init {
            mImageView = itemView.findViewById(R.id.iv_banner_background)
        }
    }

    //intent webview -> api 수정된다 해서 우선 냅두기
    fun initIntent(url: String) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(context, intent, null)
    }

}

