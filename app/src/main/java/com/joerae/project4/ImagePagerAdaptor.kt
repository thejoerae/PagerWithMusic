package com.joerae.project4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.util.zip.Inflater

class ImagePagerAdaptor (var context: Context, private var images: IntArray) : PagerAdapter() {

    // Array count
    override fun getCount(): Int {
        // "inflating" the getCount function to allow for infinite scrolling. ok - not really infinite, but if somebody
        // wants to keep going through them you'll eventually hit the end
        return images.size * 200
    }

    // Return the object to the view
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    // set up the image
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // widget
        val image: ImageView

        val inflater: LayoutInflater = context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView = inflater.inflate(R.layout.pager_item, container, false)

        // set up imageView
        image = itemView.findViewById<View>(R.id.imageView) as ImageView

        // set image to the ImageView. if position is zero - modulus won't work and you get a "divide by zero" error
        if (position == 0) {
            image.setImageResource(images[position])
        } else {
            image.setImageResource(images[position % images.size])
        }

        // add pager_item layout as current page in ViewPager

        (container as ViewPager).addView(itemView)

        return itemView
    }

    // removes pager_item
    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        (container as ViewPager).removeView(obj as ConstraintLayout)
    }

}