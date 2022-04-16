package com.example.atipik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.auth.User

class ProductAdapter(private val productList: ArrayList<products>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {







    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = productList[position]

        holder.nombre.text = currentitem.nombre
        holder.descripcion.text = currentitem.descripcion
        holder.precio.text = currentitem.precio

    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val nombre : TextView = itemView.findViewById(R.id.productName)
        val descripcion : TextView = itemView.findViewById(R.id.productDescription)
        val precio : TextView = itemView.findViewById(R.id.productPrice)


    }

}