package com.example.atipik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        registrarse()
    }

    private fun registrarse() {
        var emailText = findViewById<EditText>(R.id.email)
        var passwordText = findViewById<EditText>(R.id.password)
        var btnRegistrarse = findViewById<Button>(R.id.registrarse)

        btnRegistrarse.setOnClickListener {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(emailText.text.toString(),
                    passwordText.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful){
                            val toast = Toast.makeText(applicationContext, "Registro ok", Toast.LENGTH_LONG)
                            val intent = Intent(this, menuActivity::class.java)
                            startActivity(intent)
                        } else {
                            val toast = Toast.makeText(applicationContext, "FAIL", Toast.LENGTH_LONG)
                        }
                }
        }
    }
}