package com.example.pizzasio.data.model.panier

data class PanierModel(
    val panierItems: MutableList<PanierItem> = mutableListOf()
)

data class PanierItem(
    val name: String,
    val price: String
)

fun PanierModel.getAllCartItemsAsList(): List<PanierItem> {
    return panierItems.toList()
}
