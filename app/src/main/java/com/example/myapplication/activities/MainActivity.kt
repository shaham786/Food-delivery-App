package com.example.myapplication.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private var callbackManager: CallbackManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      FaceBook SignIN
        callbackManager = CallbackManager.Factory.create()
        val signInbtn = binding.loginButton
        signInbtn.setReadPermissions("email")
        signInbtn.setOnClickListener{
            fbsignIn()
        }

        auth = FirebaseAuth.getInstance()

//        GoogleSignIn
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signInButton.setOnClickListener {
            signInGoogle()
        }


        val signupbtn1 = binding.SignUpbtn
        signupbtn1.setOnClickListener {
            val intent = Intent(this@MainActivity, SignUp::class.java)
            startActivity(intent)
        }

        val etName: EditText = findViewById(R.id.etname)
        val etpass: EditText = findViewById(R.id.etpass)


        val loginbtn = findViewById<Button>(R.id.loginbtn)
        loginbtn.setOnClickListener {

            if (etName.text.isNullOrBlank()) {
                etName.error = "Please Enter Full Name"
                return@setOnClickListener
            } else {
                etName.text.toString()
            }

            if (etpass.text.isNullOrBlank()) {
                etpass.error = "please enter password"
                return@setOnClickListener
            } else {
                etpass.text.toString()
            }
/// for regex validation of password
/*
            if(etpass.text.matches(""))
*/


            val sharedPreferences = getSharedPreferences("UserPreference", MODE_PRIVATE)
            val name = sharedPreferences.getString("name", "")
            val password = sharedPreferences.getString("password", "")





            if (
                (etName.text.toString() != name) ||
                (etpass.text.toString() != password)
            ) {
                etName.error = "Please Enter a Valid Username"
                etpass.error = "Please Enter a Valid Password"
                return@setOnClickListener
            } else {

                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                val intent = Intent(this@MainActivity, Welcome::class.java)
                startActivity(intent)


            }


        }


//        google login status check
        if(GoogleSignIn.getLastSignedInAccount(this@MainActivity) != null){
            Log.i("SignInCheck", "onCreate: Google User Logged IN")
            val intent = Intent(this@MainActivity, Welcome::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }else{
            Log.i("SignInCheck", "onCreate: Google User Logged Out")
        }

//        FB login status check
        if(isLoggedIn()){

            Log.i("SignInCheck", "onCreate: FB User Logged In")
            val intent = Intent(this@MainActivity, Welcome::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }else{
            Log.i("SignInCheck", "onCreate: FB User Logged Out")
        }

    }

    private fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null
    }

    private fun fbsignIn() {
        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onCancel() {
                Toast.makeText(this@MainActivity, "Connection error",Toast.LENGTH_LONG).show()

            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@MainActivity, "Connection error",Toast.LENGTH_LONG).show()

            }

            override fun onSuccess(result: LoginResult) {

                handleFacebookAccessToken(result.accessToken)
                Toast.makeText(this@MainActivity, "Login Success",Toast.LENGTH_LONG).show()
                val intent = Intent(this@MainActivity, Welcome::class.java)
                startActivity(intent)
            }

        })
    }


    private fun handleFacebookAccessToken(accessToken: AccessToken?) {

        val credential = FacebookAuthProvider.getCredential(accessToken!!.token)
        auth.signInWithCredential(credential)
            .addOnSuccessListener{result->
                    result.user?.email

                Toast.makeText(this@MainActivity, "Login Successful",Toast.LENGTH_LONG).show()



            }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode,resultCode,data)
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {

        if (task.isSuccessful) {
            val account : GoogleSignInAccount? = task.result
            if(account != null){
                updateUI(account)
            }

        }
        else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                val intent = Intent(this, Welcome::class.java)
                startActivity(intent)
            }
        }

    }

}