package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.adapters.ViewPager2Adapter
import com.example.myapplication.adapters.ZoomOutPageTransformer
import com.example.myapplication.models.Fooddata
import com.example.myapplication.models.Product
import com.example.myapplication.databinding.ActivityDetailsBinding

@Suppress("DEPRECATION")
class Details : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding
    private var dataset = ArrayList<String>()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val food = intent.getSerializableExtra("food") as Product?
        food?.images?.forEach {
            dataset.add(it)
        }

        binding.Viewpager2.adapter = ViewPager2Adapter(dataset)
        binding.Viewpager2.setPageTransformer(ZoomOutPageTransformer())
        binding.wormDot.attachTo(binding.Viewpager2)

//        For Recyclerview 2(Restaurants)
        if (food != null) {

            val textView: TextView = findViewById(R.id.detailsDTv)
            val textView2: TextView = findViewById(R.id.detailsTTv)
            val details: TextView = findViewById(R.id.productDetailsTv)

            textView.text = food.description
            textView2.text = food.brand
            details.text = "Rating : " + food.rating.toString() + ""

        }
//        For RECYCLERVIEW 1 (Food onclick Details)
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