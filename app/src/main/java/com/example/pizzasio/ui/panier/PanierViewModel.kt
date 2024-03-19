package com.example.pizzasio.ui.panier

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pizzasio.data.model.panier.PanierItem
import com.example.pizzasio.data.model.pizza.PizzaModel
import com.example.pizzasio.ui.Pizzasio

class PanierViewModel : ViewModel() {
    private val panier = Pizzasio.panier
    val panierItemsState = mutableStateOf(getAllCartItemsAsList())

    fun getAllCartItemsAsList(): List<PanierItem> {
        return panier.panierItems.toList()
    }

    fun addPizzaToPanier(pizza: PizzaModel) {
        panier.panierItems.add((PanierItem(Math.random().toString(), pizza.name, pizza.price)))
        panierItemsState.value = getAllCartItemsAsList()
    }

    fun removePizzaFromPanier(panierItem: PanierItem) {
        panier.panierItems.removeIf { it.id == panierItem.id }
        panierItemsState.value = getAllCartItemsAsList()
    }

    private fun postCommandToAPI(panierItem: List<PanierItem>) {
        // insert into commande (id_client values (#USER_ID#));
    }
    private fun removeAllPanierItems(panierItem: List<PanierItem>) {
        return panier.panierItems.clear()
    }

    fun makeCommand(allPanierItems: List<PanierItem>) {
        postCommandToAPI(allPanierItems)
        removeAllPanierItems(allPanierItems)
    }
}
