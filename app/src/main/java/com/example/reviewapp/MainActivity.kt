package com.example.reviewapp

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_home)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        //homeViewModel.getNews()


    }


}