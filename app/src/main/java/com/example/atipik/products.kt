package com.example.atipik

import android.widget.Toast

class products(var descripcion : String ?= null, var nombre : String ?= null, var precio : String ?= null) {
    override fun toString(): String {
        return "products(descripcion=$descripcion, nombre=$nombre, precio=$precio)"
    }

    fun toast() {
        print("Testing")
    }

}
