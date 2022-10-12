package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import java.util.*

class ViewPagerAdapter(val context: Context, private var dataset: ArrayList<String>) : PagerAdapter() {



    override fun getCount(): Int {
        return dataset.size

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView: View = mLayoutInflater.inflate(R.layout.imagelayout, container, false)

        // on below line we are initializing
        // our image view with the id.
        val imageView = itemView.findViewById<View>(R.id.idIVImage) as ImageView

        // on below line we are setting
        // image resource for image view.
        Glide.with(imageView).load(dataset[position]).into(imageView)
        // on the below line we are adding this
        // item view to the container.
        Objects.requireNonNull(container).addView(itemView)

        return itemView
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // on below line we are removing view
        container.removeView(`object` as ConstraintLayout)
    }
}