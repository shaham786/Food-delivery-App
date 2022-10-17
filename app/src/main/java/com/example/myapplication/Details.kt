package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myapplication.databinding.ActivityDetailsBinding
import java.text.FieldPosition

class Details : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding
    private var dataset = ArrayList<String>()


    /*private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: ViewPagerAdapter*/



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val food = intent.getSerializableExtra("food") as Product?
        food?.images?.forEach {
            dataset.add(it)

        }
        binding.Viewpager2.adapter = ViewPager2Adapter(this,dataset)
        binding.Viewpager2.setPageTransformer(ZoomOutPageTransformer())
        binding.wormDot.attachTo(binding.Viewpager2)


//        binding.Viewpager2.adapter = ViewPager2Adapter(this@Details, dataset)
//        binding.Viewpager2.setPageTransformer(ZoomOutPageTransformer())
//        binding.wormDot.attachTo(binding.Viewpager2)

//        viewPager = findViewById(R.id.detailsIv)
//        val wormdots = findViewById<WormDotsIndicator>(R.id.worm_dot)
//
//
//        val food = intent.getSerializableExtra("food") as Product?
//        food?.images?.forEach {
//            imageList.add(it)
//
//        }
//        viewPagerAdapter = ViewPagerAdapter(this@Details, imageList)
//
//        viewPager.adapter = viewPagerAdapter
//        wormdots.setViewPager(viewPager)

        if (food != null) {

            val textView: TextView = findViewById(R.id.detailsDTv)
            val textView2: TextView = findViewById(R.id.detailsTTv)
            val details: TextView = findViewById(R.id.productDetailsTv)
//            val imageView : ImageView = findViewById(R.id.idIVImage)

            textView.text = food.description
            textView2.text = food.brand
            details.text = "Rating : " + food.rating.toString() + ""
//            imageView.setImageResource(food.imageView)
//            Glide.with(imageView).load(food.thumbnail).into(imageView)


        }
//        For RECYCLERVIEW 1 (Restaurants onclick Details)
        val food1 = intent.getParcelableExtra<Fooddata>("food")
        if (food1 != null) {

            val textView: TextView = findViewById(R.id.detailsDTv)
            val textView2: TextView = findViewById(R.id.detailsTTv)
            val imageView: ImageView = findViewById(R.id.detailsIv2)
            val details: TextView = findViewById(R.id.productDetailsTv)

            textView.text = food1.textView
            textView2.text = food1.textView2
            details.text = food1.details
            imageView.setImageResource(food1.imageView)





        }

        val backbtn = findViewById<ImageButton>(R.id.backbtn)
        backbtn.setOnClickListener {
            val intent = Intent(this@Details, Welcome::class.java)
            startActivity(intent)
        }
    }



}