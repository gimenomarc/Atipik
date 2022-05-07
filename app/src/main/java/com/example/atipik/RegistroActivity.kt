package com.example.atipik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity() {

    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private var db:FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference

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

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            if (nameText.text.toString().isNotEmpty() && emailText.text.toString().isNotEmpty() && passwordText.text.toString().isNotEmpty() ) {
                auth.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString()).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        lateinit var newCurrentUser : Any

                        if ( emailText.text.toString().contains("@atipik.com", ignoreCase = true) ){
                            newCurrentUser = Administrator(nameText.text.toString(), emailText.text.toString(), passwordText.text.toString())
                        } else {
                            newCurrentUser = Client(nameText.text.toString(), emailText.text.toString(), passwordText.text.toString())
                        }

                        //Coger current user
                        val currentUser = auth.currentUser

                        //PINTAR USUARIO EN DDBB TABLA USERS. CREAR TABLA CON EL CURRENTUSER Y AÑADIR VALORES DEL HASHMAP ADD.
                        dbReference.child("Users").child(currentUser!!.uid).setValue(newCurrentUser)

                        Toast.makeText(this, applicationContext.getString(R.string.cuentaOk), Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, menuActivity::class.java);
                        intent.putExtra("nameExtra", nameText.text.toString())
                        startActivity(intent)
                        finish()
                    } else {
                        print("****** isSuccessful FAIL ******")
                    }
                }
            } else {
                Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show()
            }

        }
    }
}


