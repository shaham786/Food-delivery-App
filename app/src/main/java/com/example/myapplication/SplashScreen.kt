package com.example.myapplication

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({

            val sharedPreferences = getSharedPreferences("UserPreference", MODE_PRIVATE)
            val name = sharedPreferences.getString("name", "")
            val password = sharedPreferences.getString("password", "")

            if (!name.isNullOrBlank() && !password.isNullOrBlank()) {

                val editor = sharedPreferences.edit()

                editor.apply()
                val intent = Intent(this, Welcome::class.java)
                startActivity(intent)


            } else {
                val intent = Intent(this, Welcome::class.java)
                startActivity(intent)



            }


            finish()
        },2000) // 2000 is the delayed time in milliseconds.
    }
}
