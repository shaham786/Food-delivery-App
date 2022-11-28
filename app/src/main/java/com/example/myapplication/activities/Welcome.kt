@file:Suppress("DEPRECATION")

package com.example.myapplication.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.RecyclerAdapter
import com.example.myapplication.adapters.RecyclerAdapter2
import com.example.myapplication.api.ProductApplication
import com.example.myapplication.models.Fooddata
import com.example.myapplication.models.Product
import com.example.myapplication.viewModels.MainViewModel
import com.example.myapplication.viewModels.MainViewModelFactory
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.coroutines.*


@Suppress("DEPRECATION")
class Welcome : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

    private val arrayList = ArrayList<Fooddata>()
    private var arraylist3 = ArrayList<Product>()
    private val recyclerAdapter = RecyclerAdapter(arraylist3)
    private val recyclerAdapter2 = RecyclerAdapter2(arrayList)
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
//        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#144BDE")))
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        try {

            progressBar.visibility = View.VISIBLE

            val repository = (application as ProductApplication).productsRepository
            mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
            mainViewModel.getProducts().observe(this) {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    loadDataToRecyclerView(it.products)
                }
            }
        }

        catch (e: Exception) {
            progressBar.visibility = View.VISIBLE
        }




        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API)
            .build()

        mGoogleApiClient.connect()

//        google logout
        findViewById<Button>(R.id.signOutbtn).setOnClickListener {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient)
                .setResultCallback { finish() }

//            normal logout
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("UserPreference", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

//            fb logout
            LoginManager.getInstance().logOut()
            val intent2 = Intent(this@Welcome, MainActivity::class.java)
            startActivity(intent2)
            finish()
        }


        //Recycleview1
        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchview)


        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
//
                recyclerAdapter.filter.filter(query)
                recyclerAdapter2.filter.filter(query)
                return true
            }

        })

        arrayList.add(
            Fooddata(
                "Price Rs.100",
                "Pasta",
                R.drawable.pasta,
                "White sauce has a very delicate and creamy flavour. The fat content of this tasty sauce is on the higher side as it is made up of butter, flour, and cheese which makes the sauce creamy."
            )
        )
        arrayList.add(
            Fooddata(
                "Price Rs.99",
                "Donuts",
                R.drawable.donuts,
                "A doughnut is a delicious, deep-fried breakfast treat. Most doughnuts are round and have a hole in the center. Some have frosting and sprinkles, too."
            )
        )
        arrayList.add(
            Fooddata(
                "Price Rs.58",
                "Biryani",
                R.drawable.biryani,
                "Spiced mix of meat and rice, traditionally cooked"
            )
        )
        arrayList.add(
            Fooddata(
                "Price Rs.20",
                "Pizza",
                R.drawable.pizza,
                "Dish of Italian origin consisting of a flattened disk of bread dough topped with some combination of olive oil, oregano, tomato, olives, mozzarella or other cheese, and many other ingredients, baked quickly—usually, in a commercial setting, using a wood-fired oven heated to a very high temperature—and served hot"
            )
        )
        arrayList.add(
            Fooddata(
                "Price Rs.99.00",
                "Burger",
                R.drawable.burger,
                "Tasty, delicious, and has me craving more on the first bite. to Juicy, mouthwatering, tasty, and everything you'd ever want to savor."
            )
        )


        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView2.adapter = recyclerAdapter2
        recyclerAdapter2.onItemClick = {
            val intent = Intent(this, Details::class.java)
            intent.putExtra("food", it)
            startActivity(intent)
        }

    }

    private fun loadDataToRecyclerView(list : ArrayList<Product>) {

        //RecyclerView initialize
        //API data load into recyclerView ArrayList

        val recyclerView1 = findViewById<RecyclerView>(R.id.recyclerView1)
        recyclerView1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        Log.d("ABC", arraylist3.size.toString())
        recyclerAdapter.setData(list)
        recyclerView1.adapter = recyclerAdapter
        recyclerAdapter.onItemClick1 = {
            val intent = Intent(this, Details::class.java)
            intent.putExtra("food", it)
            startActivity(intent)
        }


    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this@Welcome, "Connection Failed", Toast.LENGTH_LONG).show()

    }

    override fun onConnected(p0: Bundle?) {
        Toast.makeText(this@Welcome, "Connected", Toast.LENGTH_LONG).show()

    }

    override fun onConnectionSuspended(p0: Int) {
        Toast.makeText(this@Welcome, "Connection Suspended", Toast.LENGTH_LONG).show()

    }


}