package com.example.atipik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ANALITICS
        val analytics:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("MensajeOK", "Conexión FIREBASE ok")
        analytics.logEvent("InitScreen", bundle);

        //BOTON REGISTRAR A ACTIVITY_REGISTRO
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistro)
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent);
        }

        // SETUP
        setup()


    }

    private fun setup() {
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        val textEmail = findViewById<EditText>(R.id.txtEmail)
        val textPassword = findViewById<EditText>(R.id.textPassword)

        btnRegistro.setOnClickListener {
            if (textEmail.text.isNotEmpty() && textPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    textEmail.text.toString(),
                    textPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isComplete) {
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        alerta()
                    }
                }
            }
        }


    }

    private fun alerta() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Algo ha salido mal !")
        builder.setTitle("Error")
        builder.setPositiveButton("Acceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, privder: ProviderType) {
        val homeIntent = Intent(this, menuActivity::class.java).apply {
            putExtra("email", email)

        }
        startActivity(homeIntent)
    }


}