package com.example.atipik

data class ShoppingList (var ticketId : Long ?= null, var nameUser : String ?= null, var shopList: ArrayList<products> = ArrayList<products>(), var total : Double ?= 0.0, var pizzasString : String ?= null){

    fun buy(): Unit {
        pizzasString = ""
        shopList.forEach {
            println(it.nombre)
            pizzasString = pizzasString + ", " + it.nombre

           getTotal(total!!.toDouble() + it.precio!!.replace(",",".").toDouble())
        }
    }

    fun getTotal(price : Double) : Unit {
        total = price
    }

}
