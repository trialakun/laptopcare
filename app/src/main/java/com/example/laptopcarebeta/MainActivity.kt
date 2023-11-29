package com.example.laptopcarebeta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnkehalmasuk: Button
    private lateinit var btnkehaldaftar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnkehalmasuk = findViewById(R.id.btnkehalmasuk)
        btnkehaldaftar = findViewById(R.id.btnkehaldaftar)

        btntologinpage()
        btntoregisterpage()
    }

    private fun btntoregisterpage() {
        btnkehaldaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun btntologinpage() {
        btnkehalmasuk.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}