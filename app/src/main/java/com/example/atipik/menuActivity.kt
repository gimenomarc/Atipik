package com.example.atipik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore


class menuActivity : AppCompatActivity() {

        private lateinit var dbref : DatabaseReference
        private lateinit var productRecyclerView: RecyclerView
        private lateinit var productArrayList: ArrayList<products>

        val layoutManager = LinearLayoutManager(this)

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        productRecyclerView = findViewById(R.id.productList)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)

        productArrayList = arrayListOf<products>()

        getProducts()
    }

    private fun getProducts() {
        dbref = FirebaseDatabase.getInstance().getReference("Products")

        dbref.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (productsSnapshot in snapshot.children){
                        val product = productsSnapshot.getValue(products::class.java)
                        productArrayList.add(product!!)

                    }

                    productRecyclerView.adapter = ProductAdapter(productArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}