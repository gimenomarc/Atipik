package com.example.atipik

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    //DDBB

    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private var db:FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {

        //DDBB
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        print("*************************************************************************")
        register()
    }



    private fun register() {

        val nameText = findViewById<EditText>(R.id.nombreUsuario)
        val emailText = findViewById<EditText>(R.id.email)
        val passwordText = findViewById<EditText>(R.id.password)
        val btnRegister = findViewById<Button>(R.id.registrarse)

        val name = nameText.text.toString()
        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


        btnRegister.setOnClickListener {

            try {
                auth.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString()).addOnCompleteListener(this) {
                    print("****************************************************************************************************")
                    if (it.isSuccessful) {
                        print("Data ok")
                        val intent = Intent(this, menuActivity::class.java);
                        intent.putExtra("nameExtra", nameText.text.toString())
                        startActivity(intent)
                    } else {
                        print("Data fail")
                    }
                }

            } catch (e: Exception) {
                print("---------------------------------")
                println(e)
                print("---------------------------------")
            }
        }
    }
}


