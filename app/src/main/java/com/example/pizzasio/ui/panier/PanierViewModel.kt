package com.example.pizzasio.ui.panier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizzasio.data.model.panier.PanierItem
import com.example.pizzasio.data.model.pizza.PizzaModel
import com.example.pizzasio.ui.Pizzasio

class PanierViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    private val panier = Pizzasio.panier

    fun addPizzaToPanier(pizza: PizzaModel) {
        panier.panierItems.add((PanierItem(pizza.name, pizza.price)))
    }

    fun buildPanierDetails(panier: List<PanierItem>): String {
        val builder = StringBuilder()
        panier.forEach { pizza ->
            builder.append("${pizza.name} - ${pizza.price}\n")
        }
        return builder.toString()
    }
}
