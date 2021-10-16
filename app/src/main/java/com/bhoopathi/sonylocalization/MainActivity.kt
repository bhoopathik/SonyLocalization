package com.bhoopathi.sonylocalization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var langButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        langButton = findViewById(R.id.lang_button)
        langButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                Toast.makeText(this@MainActivity, "Button Clicked", Toast.LENGTH_SHORT).show()
            }

        })
    }
}