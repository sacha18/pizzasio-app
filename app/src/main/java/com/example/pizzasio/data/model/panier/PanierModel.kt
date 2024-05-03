package com.example.pizzasio.data.model.panier

data class PanierModel(
    val panierItems: MutableList<PanierItem> = mutableListOf()
)

data class PanierItem(
    val id: Int,
    val idPizza: String,
    val name: String,
    val size: String,
    val price: String,
)