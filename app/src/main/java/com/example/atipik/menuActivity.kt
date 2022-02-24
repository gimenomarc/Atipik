package com.example.atipik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

enum class ProviderType {
    BASIC
}

class menuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }
}