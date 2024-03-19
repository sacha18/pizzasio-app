package com.example.pizzasio.data.model.panier

data class PanierModel(
    val panierItems: MutableList<PanierItem> = mutableListOf()
)

data class PanierItem(
    val id: String,
    val name: String,
    val price: String
)