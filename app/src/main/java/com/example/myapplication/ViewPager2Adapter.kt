package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide

class ViewPager2Adapter(private val context: Context, private val dataset: ArrayList<String>)
    : RecyclerView.Adapter<ViewPager2Adapter.ViewPagerHolder>() {

    class ViewPagerHolder(view : View) : RecyclerView.ViewHolder(view) {
        val itemimage : ImageView


        init {

            itemimage = view.findViewById(R.id.idIVImage)



        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.imagelayout,parent,false)
        return ViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        Glide.with(holder.itemimage.context).load(dataset[position]).into(holder.itemimage)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }


}