package com.example.pizzasio.ui.panier

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pizzasio.data.model.panier.PanierItem
import com.example.pizzasio.data.model.pizza.PizzaModel
import com.example.pizzasio.ui.Pizzasio
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class PanierViewModel : ViewModel() {
    private val panier = Pizzasio.panier
    val panierItemsState = mutableStateOf(getAllCartItemsAsList())

    fun getAllCartItemsAsList(): List<PanierItem> {
        return panier.panierItems.toList()
    }


    fun addPizzaToPanier(pizza: PizzaModel, size: String) {
        var price = pizza.price.toBigDecimal() // Convertir le prix de la pizza en BigDecimal
        val sizePrices = mapOf(
            "S" to BigDecimal("0.8"), // Multiplicateur de prix pour la taille S
            "M" to BigDecimal("1.0"), // Multiplicateur de prix pour la taille M
            "L" to BigDecimal("1.3")  // Multiplicateur de prix pour la taille L
        )

        // Vérifier si la taille spécifiée est prise en charge
        if (sizePrices.containsKey(size)) {
            // Ajuster le prix en fonction de la taille sélectionnée
            price *= sizePrices[size] ?: BigDecimal.ONE // Utiliser le multiplicateur correspondant à la taille
        } else {
            // Taille non prise en charge, prix inchangé
            println("Taille de pizza non valide: $size")
        }

        // Formater le prix avec deux décimales et le symbole de l'euro
        val formatter = DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.FRANCE))
        val formattedPrice = formatter.format(price)

        // Ajouter la pizza au panier avec le prix ajusté et formaté
        panier.panierItems.add(
            PanierItem(
                panier.panierItems.count(),
                pizza.id,
                pizza.name,
                size,
                formattedPrice // Utiliser le prix ajusté et formaté
            )
        )
        panierItemsState.value = getAllCartItemsAsList()
    }

    fun removePizzaFromPanier(panierItem: PanierItem) {
        panier.panierItems.removeIf { it.id == panierItem.id }
        panierItemsState.value = getAllCartItemsAsList()
    }

    private fun postCommandToAPI(context: Context, panierItem: List<PanierItem>) {
        val postUrl = "https://slam.cipecma.net/2224/svilletard/Api/MakeCommande"
        val requestQueue = Volley.newRequestQueue(context)
        val postData = JSONObject()
        try {
            postData.put("id_client", Pizzasio.user.id)

            val pizzaArray = JSONArray()
            for (item in panierItem) {
                val pizzaObject = JSONObject()

                pizzaObject.put("id", item.idPizza)
                pizzaObject.put("price", item.price)
                pizzaObject.put("size", item.size)
                pizzaArray.put(pizzaObject)
            }
            postData.put("pizza", pizzaArray)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            postUrl,
            postData,
            object : Response.Listener<JSONObject?> {
                override fun onResponse(response: JSONObject?) {
                    println(response)
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    error.printStackTrace()
                }
            })

        requestQueue.add(jsonObjectRequest)
    }

    fun removeAllPanierItems(panierItem: List<PanierItem>) {
        panier.panierItems.clear()
        panierItemsState.value = getAllCartItemsAsList()
    }

    fun makeCommand(context: Context, allPanierItems: List<PanierItem>) {
        postCommandToAPI(context, allPanierItems)
    }
}
