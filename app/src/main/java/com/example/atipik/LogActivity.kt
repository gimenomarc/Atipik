package com.example.atipik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

lateinit var dbref: DatabaseReference
private lateinit var logRecyclerView: RecyclerView
private lateinit var logArrayList: ArrayList<ShoppingList>
private lateinit var auth: FirebaseAuth

class LogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        auth = FirebaseAuth.getInstance()

        logRecyclerView = findViewById(R.id.recyclerViewLogs)
        logRecyclerView.layoutManager = LinearLayoutManager(this)
        logRecyclerView.setHasFixedSize(true)
        logArrayList = arrayListOf()
        logRecyclerView.adapter = NewLogAdapter(logArrayList)

        getLogs()
        logOut()
    }

    private fun getLogs() {
        dbref = FirebaseDatabase.getInstance().getReference("Logs")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (logSnapshot in snapshot.children.reversed()) {
                        val log = logSnapshot.getValue(ShoppingList::class.java)
                        logArrayList.add(log!!)
                    }
                    logRecyclerView.adapter = NewLogAdapter(logArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("****** onCancelled ******")
            }
        })
    }

    private fun logOut() {

        val btnLogOut = findViewById<ImageButton>(R.id.btnLogOutBackoffice)
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

class NewLogAdapter(private val logList: ArrayList<ShoppingList>) :
    RecyclerView.Adapter<NewLogAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.log_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = logList[position]

        holder.logUserName.text = currentItem.nameUser
        holder.logPizzas.text = currentItem.pizzasString
        holder.logPrice.text = currentItem.total.toString()

    }

    override fun getItemCount(): Int {
        return logList.size
    }

    class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val logUserName: TextView = itemView.findViewById(R.id.logName)
        val logPizzas: TextView = itemView.findViewById(R.id.logPizzas)
        val logPrice: TextView = itemView.findViewById(R.id.logTotalPrice)


    }

}