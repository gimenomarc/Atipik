package com.example.atipik

class Administrator (username : String ?= null,email : String ?= null,password : String ?= null, type_user : String = "Admin") : User(username, email, password, type_user){

}