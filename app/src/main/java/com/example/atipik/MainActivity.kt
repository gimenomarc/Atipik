package com.example.atipik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference

        //Analitics Eventos
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "FirebaseOk")
        analytics.logEvent("InitScreen", bundle)

        setup()
    }

    //FUNCTION LOGIC LOGIN
    private fun setup() {
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        val textEmail = findViewById<EditText>(R.id.txtEmail)
        val textPassword = findViewById<EditText>(R.id.textPassword)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        btnRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java);
            startActivity(intent)
        }

        btnIniciarSesion.setOnClickListener {
            if (textEmail.text.isNotEmpty() && textPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        textEmail.text.toString(),
                        textPassword.text.toString()
                    )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            val currentUser = auth.currentUser

                            dbReference.child("Users").child(currentUser!!.uid).get()
                                .addOnSuccessListener {
                                    println(it.child("Type_user").value)
                                    if (it.child("Type_user").value.toString() == "Client") {
                                        val nameOfUser = it.child("Name").value.toString()
                                        val intent = Intent(this, MenuActivity::class.java)
                                        intent.putExtra("nameExtra", nameOfUser)
                                        startActivity(intent)
                                        finish()
                                    } else if (it.child("Type_user").value.toString() == "Admin") {
                                        val intent = Intent(this, LogActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                        }
                    }
            } else if (textEmail.text.isEmpty()) {
                Toast.makeText(
                    this,
                    applicationContext.getString(R.string.loginFail),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (textPassword.text.isEmpty()) {
                Toast.makeText(
                    this,
                    applicationContext.getString(R.string.loginFail),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}