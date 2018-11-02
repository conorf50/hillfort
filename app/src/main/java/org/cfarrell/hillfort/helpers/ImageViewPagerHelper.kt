package org.cfarrell.hillfort.helpers


import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.util.ArrayList


/*
This code was taken from here:
https://www.youtube.com/watch?v=tM7rwJoK-18
(auto converted to Kotlin by Android Studio)
Source: codinginflow (Youtube)

The library was changed to Glide using this guide here:
https://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en

 */


class ImageViewPagerHelper internal constructor(private val context: Context, private val imageUrls: ArrayList<String>) :
    PagerAdapter() {

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        Glide.with(context)
            .load(imageUrls[position])
            .into(imageView)
        container.addView(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}