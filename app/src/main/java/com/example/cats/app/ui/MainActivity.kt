package com.example.cats.app.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.cats.R

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, ImagesFragment())
            .commit()
    }
}