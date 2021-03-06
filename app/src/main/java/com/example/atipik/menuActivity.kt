package com.example.atipik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import kotlin.collections.ArrayList

var currentTime: Long = System.currentTimeMillis() / 1000L
var shoppingCart: ShoppingList = ShoppingList(currentTime)

class MenuActivity : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productArrayList: ArrayList<Products>
    private lateinit var auth: FirebaseAuth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)



        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        productRecyclerView = findViewById(R.id.productList)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)
        productArrayList = arrayListOf()
        productRecyclerView.adapter = ProductAdapter(productArrayList)


        //EXTRA HELLO USER
        val textLayoutExtras = findViewById<TextView>(R.id.extraName)
        val bundle = intent.extras
        val nameExtra = bundle?.getString("nameExtra").toString().uppercase()
        textLayoutExtras.text = "Bienvenido $nameExtra"
        shoppingCart.nameUser = nameExtra


        //FUNCTION GET PRODUCTS
        getProducts()

        //BUTTON BUY PRODUCTS
        buyProducts()

        //LOGOUT BTN
        logOut()
    }

    private fun getProducts() {
        dbref = FirebaseDatabase.getInstance().getReference("Products")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (productsSnapshot in snapshot.children) {
                        val product = productsSnapshot.getValue(Products::class.java)
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

    private fun buyProducts() {

        val btnBuyProducts = findViewById<Button>(R.id.btnRealizarCompra)
        btnBuyProducts.setOnClickListener {

            dbref =
                FirebaseDatabase.getInstance().getReference("Logs").child(currentTime.toString())

            //PINTAR DATOS EN DDBB TABLA LOGS.
            shoppingCart.buy()
            dbref.setValue(shoppingCart)
            Toast.makeText(
                this,
                applicationContext.getString(R.string.toastCompraOk),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun logOut() {

        val btnLogOut = findViewById<ImageButton>(R.id.btnLogOut)
        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                this,
                applicationContext.getString(R.string.btnLogOut),
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }
    }
}

class ProductAdapter(private val productList: ArrayList<Products>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.product_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = productList[position]

        holder.name.text = currentItem.nombre
        holder.description.text = currentItem.descripcion
        holder.price.text = currentItem.precio

        holder.itemView.setOnClickListener {
            println(currentItem.precio)
            shoppingCart.shopList.add(currentItem)
            println("**************************")
            println(shoppingCart)
            println(shoppingCart.shopList.size)

            holder.itemView.setBackgroundResource(R.color.onClickItem)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = itemView.findViewById(R.id.productName)
        val description: TextView = itemView.findViewById(R.id.productDescription)
        val price: TextView = itemView.findViewById(R.id.productPrice)


    }

}




