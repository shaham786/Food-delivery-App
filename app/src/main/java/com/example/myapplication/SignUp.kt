package com.example.myapplication


import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.models.UserInfo


class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val etName: EditText = findViewById(R.id.etname)
        val etUname: EditText = findViewById(R.id.etUname)
        val etemail: EditText = findViewById(R.id.etemail)
        val etpass: EditText = findViewById(R.id.etpass)
        val signupbtn2 = findViewById<Button>(R.id.SignUpbtn2)
        val genderGroup = findViewById<RadioGroup>(R.id.grpbtn)


        signupbtn2.setOnClickListener {
            val name: String

            if (etName.text.isNullOrBlank()) {
                //no Value Entered
                //Toast plus Edit Text Error Message
                etName.error = "Please Enter Full Name"
                return@setOnClickListener
            } else {
                //Value Entered
                name = etName.text.toString()
            }
            val uname: String
            if (etUname.text.isNullOrBlank()) {
                etUname.error = "Please Enter User Name"
                return@setOnClickListener
            } else {
                uname = etUname.text.toString()
            }
            val email: String
            if (etemail.text.isNullOrBlank()) {
                etemail.error = "Please Enter Email"
                return@setOnClickListener
            } else {
                email = etemail.text.toString()
            }
            val password: String
            if (etpass.text.isNullOrBlank()) {
                etpass.error = "Please Enter password"
                return@setOnClickListener
            } else {
                password = etpass.text.toString()
            }

            when (genderGroup.checkedRadioButtonId) {
                R.id.btnMale -> {

                }

                R.id.btnFmale -> {

                }
                else -> {
                    Toast.makeText(this@SignUp, "Please select one", Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener

                }

            }

            val sharedPreferences =
                getSharedPreferences("UserPreference", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.putString("Uname", uname)
            editor.putString("email", email)
            editor.putString("password", password)
            editor.apply()






            UserInfo(name, uname, email, password)
            Toast.makeText(this@SignUp, "User Created Successfully", Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }
}
