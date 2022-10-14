@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class Welcome : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

    private val arrayList = ArrayList<Fooddata>()
    private val arraylist3 = ArrayList<Product>()
    private val recyclerAdapter = RecyclerAdapter(arraylist3)
    private val recyclerAdapter2 = RecyclerAdapter2(arrayList)

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
//        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#144BDE")))
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        try {

            progressBar.visibility = View.VISIBLE
            GlobalScope.launch {
                val productsAPI = RetrofitHelper.getInstance1().create(ProductsApi::class.java)

                val product = productsAPI.getProducts(1)
                if (product != null) {

                    val productList = product.body()
                    productList?.products?.forEach {
                        Log.d("API", it.description)
                        arraylist3.add(
                            it
                        )
                    }
                    runOnUiThread {
                        progressBar.visibility = View.GONE
                        loadDataToRecyclerView()
                    }

                }

            }
        } catch (e: Exception) {
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

        findViewById<Button>(R.id.signOutbtn).setOnClickListener {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient)
                .setResultCallback { finish() }
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("UserPreference", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()



            LoginManager.getInstance().logOut()
            val intent2 = Intent(this@Welcome, MainActivity::class.java)
            startActivity(intent2)
            finish()
        }


        //Recycleview1
        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchview)


//        arrayList2.add(
//            Fooddata(
//                "Delivery Rs.70", "Burger King", R.drawable.burgerking,
//                "Islamabad\n" + "Contact No.0312 7855523\n" + "Open ⋅ Closes 12:01AM\n" + "Dine-in·\n" +
//                        "Drive-through·\n" + "No-contact delivery"
//            )
//        )
//        arrayList2.add(
//            Fooddata(
//                "Delivery Rs.90", "Subway", R.drawable.subway,
//                "Johar Rd\n" + "F-8/4 Markaz\n" + "Islamabad\n" + "Open ⋅ Closes 12:01AM\n" +
//                        "Dine-in·\n" + "Drive-through·\n" + "No-contact delivery"
//            )
//        )
//        arrayList2.add(
//            Fooddata(
//                "Delivery Rs.50", "KFC", R.drawable.kfc,
//                "F8/4 Islamabad\n" + "Open ⋅ Closes 12:01AM\n" + "Dine-in\n" +
//                        "Drive-through·\n" + "No-contact delivery"
//            )
//        )
//        arrayList2.add(
//            Fooddata(
//                "Delivery Rs.60", "Pizza Hut", R.drawable.pizzahut,
//                "F8/4 Islamabad\n" + "Open ⋅ Closes 12:01AM\n" + "Dine-in·\n" +
//                        "Drive-through·\n" + "No-contact delivery"
//            )
//        )
//        arrayList2.add(
//            Fooddata(
//                "Delivery Rs.80", "Mc Donald's", R.drawable.mcdonalds,
//                "F8/4 Islamabad\n" + "Open ⋅ Closes 12:01AM\n" +
//                        "Dine-in·\n" + "Drive-through\n" + "No-contact delivery"
//            )
//        )
//        arrayList2.add(
//            Fooddata(
//                "Delivery Rs.80", "Domino's", R.drawable.dominos,
//                "F8/4 Islamabad\n" + "Open ⋅ Closes 12:01AM\n" + "Dine-in·\n" +
//                        "Drive-through·\n" + "No-contact delivery"
//            )
//        )
//        arrayList2.add(
//            Fooddata(
//                "Delivery Rs.80", "Texas Chicken", R.drawable.texas,
//                "F8/4 Islamabad\n" + "Open ⋅ Closes 12:01AM\n" + "Dine-in·\n" +
//                        "Drive-through·\n" + "No-contact delivery"
//            )
//        )


        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
//                Log.d("onQueryTextChange", "query: $query")
                recyclerAdapter.filter.filter(query)
                recyclerAdapter2.filter.filter(query)
                return true
            }

        })

//        recyclerAdapter.notifyDataSetChanged()
//        recyclerView1.adapter = recyclerAdapter
//        recyclerAdapter.onItemClick1 = {
//            val intent = Intent(this, Details::class.java)
//            intent.putExtra("food", it)
//            startActivity(intent)
//        }

        //    2nd recyclerView


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

    private fun loadDataToRecyclerView() {
        //RecyclerView initialize
        //API data load into recyclerView ArrayList


        val recyclerView1 = findViewById<RecyclerView>(R.id.recyclerView1)
        recyclerView1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        Log.d("ABC", arraylist3.size.toString())
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