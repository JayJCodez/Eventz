package com.example.optionsmenupractice.main.activities

import Database.DBHelper
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.optionsmenupractice.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var firstnameText: EditText
    private lateinit var lastNameText: EditText
    private lateinit var usernameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var dbHelper: DBHelper
    private lateinit var registerbutton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.registertoolbar)
        setSupportActionBar(toolbar)

        firstnameText = findViewById(R.id.firstNameTxt)
        lastNameText = findViewById(R.id.lastNameTxt)
        usernameText = findViewById(R.id.usernameTxt)
        passwordText = findViewById(R.id.reg_passwordTxt)
        registerbutton = findViewById(R.id.registerBTN)
        dbHelper = DBHelper(this)

        registerbutton.setOnClickListener() {
            val firstnametext = firstnameText.text.toString()
            val unametext = usernameText.text.toString()
            val lastnametext = lastNameText.text.toString()
            val passwordtxt = passwordText.text.toString()
            val savedata = dbHelper.inserdata(unametext, firstnametext, lastnametext, passwordtxt)

            if (TextUtils.isEmpty(unametext) || TextUtils.isEmpty(firstnametext) || TextUtils.isEmpty(
                    lastnametext
                ) || TextUtils.isEmpty(passwordtxt)
            ) {
                Toast.makeText(this, "Fill in registration details", Toast.LENGTH_LONG).show()
            } else {
                register(unametext, passwordtxt, firstnametext, lastnametext)
            }

//            if (savedata == true){
//                Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show()
//            }
        }


    }


    private fun register(username: String, password: String, firstname: String, lastname: String) {

        val url = "http://10.0.2.2:8888/register.php"
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Handle the response
                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                // Handle the error
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf(
                    "username" to username,
                    "password" to password,
                    "firstname" to firstname,
                    "lastname" to lastname
                )
            }
        }

        requestQueue.add(stringRequest)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent = Intent(this, LoginActivity::class.java)

        when (item.itemId) {

            R.id.loginNav -> startActivity(intent)

        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun validateEmptyFields(
            firstName: String,
            lastName: String,
            username: String,
            password: String
        ): Boolean {
            return firstName.isNotEmpty() && lastName.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()
        }

        fun performRegistration(
            firstName: String,
            lastName: String,
            username: String,
            password: String
        ): Boolean {
            // Logic to perform registration goes here
            // Return true if registration is successful, false otherwise
            return true
        }

    }
}