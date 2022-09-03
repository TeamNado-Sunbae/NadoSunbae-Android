package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemHomeBannerBinding
import com.nadosunbae_android.domain.model.home.Banner

class BannerListAdapter(var data: List<Banner>) : RecyclerView.Adapter<BannerListAdapter.BannerViewHolder>() {

    class BannerViewHolder(val binding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            ItemHomeBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
//        holder.binding.setVariable(BR.Banner, getItem(position))
//        if (data.isNotEmpty()) {
//            val builder =
//                SpannableStringBuilder((position % data.size + 1).toString() + " / " + data.size)
//            val span = ForegroundColorSpan(Color.WHITE)
//            builder.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
//            holder.binding.banner = (data[position % data.size])
//            Timber.d("${(position % data.size)}")
//            holder.binding.position = (position % data.size)
//            holder.binding.tvIndex.text = builder
//            holder.binding.root.setOnClickListener {
//                clickBanner(data[position % data.size].music._id)
//            }
//        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}