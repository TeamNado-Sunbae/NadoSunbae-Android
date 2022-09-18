package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.content.Context
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.util.getBackgroundImage
import com.nadosunbae_android.domain.model.app.AppBannerData


class BannerListAdapter(val context: Context) : PagerAdapter() {

    private var layoutInflater : LayoutInflater?= null
    var Image = ArrayList<String>()

    private val dataList: ArrayList<AppBannerData>? = null

    override fun getCount(): Int {
        return Image.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.item_home_banner, null)
        val image = v.findViewById<View>(R.id.iv_banner_background) as ImageView

        image.setImageResource(dataList!!.get(position).getBackgroundImage(position))
        val vp = container as ViewPager2
        vp.addView(v,0)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager2
        val v = `object` as View
        vp.removeView(v)
    }
}