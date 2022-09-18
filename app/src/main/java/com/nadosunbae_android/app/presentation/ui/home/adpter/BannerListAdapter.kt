package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadosunbae_android.app.R


class BannerListAdapter(private val context: Context, private val sliderImage: List<String>) :
    RecyclerView.Adapter<BannerListAdapter.BannerListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerListHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_banner, parent, false)
        return BannerListHolder(view)
    }

    override fun onBindViewHolder(holder: BannerListHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position])
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }

    inner class BannerListHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val mImageView: ImageView
        fun bindSliderImage(imageURL: String?) {
            Glide.with(context)
                .load(imageURL)
                .into(mImageView)
        }

        init {
            mImageView = itemView.findViewById(R.id.iv_banner_background)
        }
    }
}