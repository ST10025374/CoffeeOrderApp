package com.example.myfirstapp_opsc7311

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirstapp_opsc7311.databinding.ActivityMainBinding
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    var order = Order()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView2.setOnClickListener(this)
        binding.imageView3.setOnClickListener(this)
        binding.imageView4.setOnClickListener(this)
        binding.imageView5.setOnClickListener(this)
        binding.imageView6.setOnClickListener(this)
        binding.imageView7.setOnClickListener(this)

        setSupportActionBar(binding.navToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.navToolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.bringToFront()
        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageView2 -> order.productName = "Soy Latte"
            R.id.imageView3 -> order.productName = "Chocco Frap"
            R.id.imageView4 -> order.productName = "Bottled Americano"
            R.id.imageView5 -> order.productName = "Rainbow Frapp"
            R.id.imageView6 -> order.productName = "Caramel Frapp"
            R.id.imageView7 -> order.productName = "Black Forest Frapp"
        }

        Toast.makeText(this, "MMM" + order.productName, Toast.LENGTH_SHORT).show()
        openIntent(this, order.productName, OrderDetailsActivity::class.java)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_photo -> openIntent(this, "", CoffeeSnapsActivity::class.java)
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}