package com.example.atipik

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import androidx.appcompat.view.menu.ActionMenuItemView
import com.google.firebase.firestore.auth.User

class menuActivity : AppCompatActivity() {

        private lateinit var dbref : DatabaseReference
        private lateinit var productRecyclerView: RecyclerView
        private lateinit var productArrayList: ArrayList<products>

        private lateinit var ProductAdapter : RecyclerView;

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        productRecyclerView = findViewById(R.id.productList)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)
        productArrayList = arrayListOf<products>()
        productRecyclerView.adapter = ProductAdapter(productArrayList)


        //EXTRA HELLO USER
        var textLayoutExtras = findViewById<TextView>(R.id.extraName)
        val bundle = intent.extras
        val nameExtra = bundle?.getString("nameExtra").toString().uppercase()
        textLayoutExtras.text = "Bienvenido $nameExtra"

        //FUNCTION GET PRODUCTS
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
                print("****** onCancelled ******")
            }
        })
    }
}

class ProductAdapter (private val productList: ArrayList<products>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item,
            parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = productList[position]

        holder.name.text = currentitem.nombre
        holder.description.text = currentitem.descripcion
        holder.price.text = currentitem.precio
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        val name : TextView = itemView.findViewById(R.id.productName)
        val description : TextView = itemView.findViewById(R.id.productDescription)
        val price : TextView = itemView.findViewById(R.id.productPrice)
    }
}




