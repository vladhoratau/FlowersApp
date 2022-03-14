package com.example.flowersapp.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flowersapp.R
import com.example.flowersapp.utils.Navigator
import com.example.flowersapp.views.fragments.OrderListFragment

class MainActivity : AppCompatActivity() {

    private val navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.replaceFragment((OrderListFragment.newInstance()), this)
    }
}