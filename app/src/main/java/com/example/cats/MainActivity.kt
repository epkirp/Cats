package com.example.cats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import io.realm.Realm

class MainActivity : MvpAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, ImagesFragment())
            .commit()

    }
}