package com.example.pizzasio.ui.panier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizzasio.data.model.panier.PanierItem
import com.example.pizzasio.data.model.pizza.PizzaModel
import com.example.pizzasio.ui.Pizzasio

class PanierViewModel : ViewModel() {
    private val panier = Pizzasio.panier

    fun addPizzaToPanier(pizza: PizzaModel) {
        panier.panierItems.add((PanierItem(pizza.name, pizza.price)))
    }
}
