package com.example.mytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mytest.viewmodel.EpisoDateViewModel

class tvShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show)

        val viewmodel = ViewModelProvider(this).get(EpisoDateViewModel::class.java)
        title = "Tv Shows"
    }
}