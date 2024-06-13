package com.example.myfirstapp_opsc7311

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirstapp_opsc7311.databinding.ActivityOrderDetailsBinding

class OrderDetailsActivity : AppCompatActivity() {
    var order = Order()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        order.productName = intent.getStringExtra("order").toString()

        binding.txtPlacedOrder.text = order.productName

        when (order.productName)
        {
            "Soy Latte" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb1)
            "Chocco Frapp" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb2)
            "Bottled Americano" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb3)
            "Rainbow Frapp" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb4)
            "Caramel Frapp" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb5)
            "Black Forest Frapp" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb6)
        }

        binding.fab.setOnClickListener()
        {
            shareIntent(applicationContext, order.productName)
        }
    }
}