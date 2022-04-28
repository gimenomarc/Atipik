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
    private lateinit var auth: FirebaseAuth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        var mock : MutableList<HashMap<String, Any>> = mutableListOf<HashMap<String, Any>>()
        var mockMap : HashMap<String, Any> = HashMap<String, Any>()
        mockMap["user"] = "Jorge"
        mockMap["pizzas"] = "Test"
        mockMap["total"] = 1.2
        mock.add(mockMap)
        logRecyclerView = findViewById(R.id.recyclerViewLogs)
        logRecyclerView.layoutManager = LinearLayoutManager(this)
        logRecyclerView.setHasFixedSize(true)
        logRecyclerView.adapter = LogAdapter(mock)
    }

    private fun getLogs(): MutableList<HashMap<String, Any>> {

        val listCards: MutableList<HashMap<String, Any>> = mutableListOf<HashMap<String, Any>>()
        dbref = FirebaseDatabase.getInstance().getReference("Logs")

        dbref.get().addOnSuccessListener {
            val children = it!!.children
            var counter = it.childrenCount
            children.forEachIndexed { index, el ->
                var childrenNested = el!!.children
                val card = HashMap<String, Any>()
                childrenNested.forEachIndexed { i, element ->
                    if (element.key == "nameUser") {
                        card["user"] = element.getValue(String::class.java) as String
                    }
                    if (element.key == "pizzasString") {
                        card["pizzas"] = element.getValue(String::class.java) as String
                    }
                    if (element.key == "total") {
                        card["total"] = element.getValue(Double::class.java) as Double
                    }
                }
                listCards.add(card)
                if (counter.toInt() - 1 == index) {
                    println("///////////////")
                    println(listCards)

                }
            }

        }
        //logRecyclerView.adapter = LogAdapter(listCards)
        println(listCards)
        return listCards
    }


}

class LogAdapter(var cardList: MutableList<HashMap<String, Any>>) :
    RecyclerView.Adapter<LogAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        println(cardList)
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.log_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.name.text = "test1"
        holder.pizzas.text = "test2"
        holder.totalPrice.text = "23"

    }


    override fun getItemCount(): Int {

        return 0

    }


    class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.logName)
        val pizzas: TextView = itemView.findViewById(R.id.logPizzas)
        val totalPrice: TextView = itemView.findViewById(R.id.logTotalPrice)
    }

}