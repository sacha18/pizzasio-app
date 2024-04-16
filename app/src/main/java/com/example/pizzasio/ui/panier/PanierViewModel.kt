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

class PanierViewModel : ViewModel() {
    private val panier = Pizzasio.panier
    val panierItemsState = mutableStateOf(getAllCartItemsAsList())

    fun getAllCartItemsAsList(): List<PanierItem> {
        return panier.panierItems.toList()
    }

    fun addPizzaToPanier(pizza: PizzaModel) {
        panier.panierItems.add((PanierItem(panier.panierItems.count(), pizza.id, pizza.name, pizza.price)))
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
                pizzaObject.put("size", "xl")
                pizzaArray.put(pizzaObject)
            }
            postData.put("pizza", pizzaArray)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d("TAG", "postData: "+postData)
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
