package com.aigeniusx.helpers

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        // get reference to button
        val btn_finder_helper = findViewById(R.id.button1) as Button
        // set on-click listener
        btn_finder_helper.setOnClickListener {
            // your code to perform when the user clicks on the button
            setContentView(R.layout.activity_main_needy)
        }

        // get reference to button
        val btn_become_helper = findViewById(R.id.button2) as Button
        // set on-click listener
        btn_become_helper.setOnClickListener {
            // your code to perform when the user clicks on the button
            setContentView(R.layout.activity_main_needy)
        }
    }
}