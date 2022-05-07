package com.example.atipik

data class ShoppingList(
    var ticketId: Long? = null,
    var nameUser: String? = null,
    var shopList: ArrayList<Products> = ArrayList(),
    var total: Double? = 0.0,
    var pizzasString: String? = null
) {

    fun buy(){
        pizzasString = ""
        shopList.forEach {
            println(it.nombre)
            pizzasString = pizzasString + ", " + it.nombre

            getTotal(total!!.toDouble() + it.precio!!.replace(",", ".").toDouble())
        }
    }

    private fun getTotal(price: Double){
        total = Math.round(price * 1000.0) / 1000.0
        total = price
    }
}










