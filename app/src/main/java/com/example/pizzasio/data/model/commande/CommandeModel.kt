package com.example.pizzasio.data.model.commande

data class CommandeModel(
    val id: String,
    val date: String,
    val price: String,
    val ligneCommande: MutableList<LigneCommande> = mutableListOf()
    )

data class LigneCommande(
    val name: String,
    val price: String,
    val size: String,
)