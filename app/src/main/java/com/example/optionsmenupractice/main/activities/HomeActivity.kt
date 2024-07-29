package com.example.optionsmenupractice.main.activities

import com.example.optionsmenupractice.fragments.Dashboard
import com.example.optionsmenupractice.fragments.ProfileFragment
import com.example.optionsmenupractice.fragments.HomeFragment
import com.example.optionsmenupractice.fragments.MyEvents
import com.example.optionsmenupractice.fragments.SettingsFragment
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.optionsmenupractice.R
import com.google.android.material.navigation.NavigationView
import com.example.optionsmenupractice.fragments.SavedEvents
import fragments.NewEvent

class HomeActivity : AppCompatActivity(R.layout.activity_home_page), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var name : String
    private lateinit var id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        drawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.navigation)

        val toolbar: Toolbar = findViewById(R.id.homepagetoolbar)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24)

        navView.setNavigationItemSelectedListener(this)

        val bundle = intent.extras

        name = bundle?.getString("first_name").toString()
        id = bundle?.getString("user_id").toString()

        val welcometext = "Welcome, " + name

        openFragment(Dashboard())

        // Access the header view
        val headerView = navView.getHeaderView(0)
        // Find the TextView in the header view
        val welcomeText: TextView = headerView.findViewById(R.id.welcomeLabel)
        // Set the text to the desired value
        welcomeText.text = welcometext
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return when (item.itemId) {
            R.id.signoutBTN -> {
                startActivity(Intent(this, LoginActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> openFragment(HomeFragment())
            R.id.dashboard -> openFragment(Dashboard())
            R.id.profile -> openFragment(ProfileFragment())
            R.id.savedevents -> openFragment(SavedEvents())
            R.id.myevent -> openFragment(MyEvents())
            R.id.newevent -> openFragment(NewEvent())
            R.id.settings -> openFragment(SettingsFragment())
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun openFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putString("user_name", name)
        bundle.putString("user_id", id)

        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("null")
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.homepage_options_menu, menu)

        super.onCreateOptionsMenu(menu)

        return true
    }

}