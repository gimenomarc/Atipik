package com.example.atipik

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.log_item.*

class AdminActivity : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var logRecyclerView: RecyclerView
    private lateinit var logArrayList: ArrayList<ShoppingList>
    private lateinit var auth: FirebaseAuth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        logRecyclerView = findViewById(R.id.recyclerViewLogs)
        logRecyclerView.layoutManager = LinearLayoutManager(this)
        logRecyclerView.setHasFixedSize(true)

        logArrayList = arrayListOf<ShoppingList>(
            ShoppingList(
                1,
                "name",
                arrayListOf<products>(products("test", "test", "test", "test"))
            ),
            ShoppingList(1, "name", arrayListOf<products>(products("test", "test", "test", "test")))
        )
        logRecyclerView.adapter = LogAdapter(logArrayList)

        getLogs()
    }

    private fun getLogs() {

        dbref = FirebaseDatabase.getInstance().getReference("Logs")

        dbref.get().addOnSuccessListener {
            val children = it!!.children
            children.forEach {
                println(it.child("shopList"))
                println(it.toString())
            }

        }
        //logRecyclerView.adapter = LogAdapter(logArrayList)


    }


}

class LogAdapter(private val logList: ArrayList<ShoppingList>) :
    RecyclerView.Adapter<LogAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.log_item,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = logList[position]

        holder.name.text = "test1"
        holder.pizzas.text = "test2"
        holder.totalPrice.text = "23"

    }

    override fun getItemCount(): Int {

        return logList.size

    }

    class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.logName)
        val pizzas: TextView = itemView.findViewById(R.id.logPizzas)
        val totalPrice: TextView = itemView.findViewById(R.id.logTotalPrice)
    }

}