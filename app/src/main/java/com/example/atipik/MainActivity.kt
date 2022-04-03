package com.example.atipik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Analitics Eventos
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "FirebaseOk")
        analytics.logEvent("InitScreen", bundle)

        setup()
    }

    //Intent para pasar a actiivty Registrarse
    fun goRegistrarse (view: View) {
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistro);
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }

    //Intent goHome
    fun goGome (view: View) {
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val intent = Intent(this, menuActivity::class.java)
        startActivity(intent)
    }

    private fun setup() {
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        var textEmail = findViewById<EditText>(R.id.txtEmail)
        var textPassword = findViewById<EditText>(R.id.textPassword)


        btnRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java);
            startActivity(intent)
        }

        btnIniciarSesion.setOnClickListener {
            if (textEmail.text.isNotBlank() && textPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(textEmail.text.toString(),
                        textPassword.text.toString()).addOnCompleteListener {
                            if (it.isSuccessful) {
                                val toast = Toast.makeText(applicationContext, "Registro ok", Toast.LENGTH_SHORT)
                                val intent = Intent(this, menuActivity::class.java)
                                startActivity(intent)
                            } else {
                                val toast = Toast.makeText(applicationContext, "FAIL", Toast.LENGTH_SHORT)
                            }
                    }
            }
        }
    }



}