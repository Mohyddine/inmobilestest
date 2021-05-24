package com.mehyo.inmobilestest

import java.io.Serializable

//single owner data class
data class Owner(val id:Int, val login:String,val avatar_url:String):Serializable
//single item data class
data class Item( val id:Int, val name:String,val full_name:String,val owner:Owner):Serializable
//Response model data class
data class GitModel( val items:ArrayList<Item>)