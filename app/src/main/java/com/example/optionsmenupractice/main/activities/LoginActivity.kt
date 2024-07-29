package com.example.optionsmenupractice.main.activities

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.optionsmenupractice.R
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LoginActivity() : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var usernameTxt: EditText
    lateinit var passwordTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        loginButton = findViewById(R.id.login_button)
        usernameTxt = findViewById(R.id.usernameTXT)
        passwordTxt = findViewById(R.id.passwordtxt)

        loginButton.setOnClickListener {
            loginUser()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.registerNav -> {
                val register = Intent(this, RegisterActivity::class.java)
                startActivity(register)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun loginUser() {
        val username = usernameTxt.text.toString()
        val password = passwordTxt.text.toString()

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            showToast("Please fill in login details")
        } else {
            AuthenticationTask(this).execute(username, password)
        }
    }

    fun showToast(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Login Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    open class AuthenticationTask(context: Context) : AsyncTask<String, Void, Pair<Boolean, Pair<String, String>>>() {

        private val contextRef: WeakReference<Context> = WeakReference(context)

        public override fun doInBackground(vararg params: String): Pair<Boolean, Pair<String, String>> {
            val username = params[0]
            val password = params[1]

            var urlConnection: HttpURLConnection? = null
            var success = false
            var responseMessage = ""

            try {
                val baseUrl = "http://10.0.2.2:8888/login.php"
                val url = URL(baseUrl)
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "POST"
                urlConnection.doOutput = true

                val postData =
                    "user_name=${URLEncoder.encode(username, "UTF-8")}&password=${URLEncoder.encode(password, "UTF-8")}"
                urlConnection.outputStream.use { outputStream ->
                    outputStream.write(postData.toByteArray())
                }

                val responseCode = urlConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = urlConnection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    responseMessage = reader.readLine()
                    val json = JSONObject(responseMessage)
                    val userId = json.getString("User ID")
                    success = true
                    return Pair(success, Pair(responseMessage, userId))
                } else {
                    responseMessage = "Login failed"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                responseMessage = "An error occurred"
            } finally {
                urlConnection?.disconnect()
            }

            return Pair(success, Pair(responseMessage, ""))
        }

        public override fun onPostExecute(result: Pair<Boolean, Pair<String, String>>) {
            val (success, data) = result
            val (message, userId) = data
            val context = contextRef.get()
            if (context != null) {
                if (success) {
                    (context as LoginActivity).showToast(message)
                    val intent = Intent(context, HomeActivity::class.java)
                    intent.putExtra("user_id", userId)
                    context.startActivity(intent)
                } else {
                    (context as LoginActivity).showErrorDialog(message)
                }
            }
        }

    }

}
