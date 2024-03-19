import android.app.Application
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.pizzasio.data.model.pizza.PizzaModel

class PizzaViewModel {

    interface PizzaCallback {
        fun onDataLoaded(pizzaList: List<PizzaModel>)
        fun onError(message: String)
    }

    fun loadPizza(application: Application, callback: PizzaCallback) {
        val allPizza = mutableListOf<PizzaModel>()

        // Créer une file de requêtes Volley
        val queue = Volley.newRequestQueue(application)
        val apiUrl = "https://slam.cipecma.net/jsabbah/Api/AllPizza"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, apiUrl, null,
            { response ->
                for (i in 0 until response.length()) {
                    val pizzaJson = response.getJSONObject(i)
                    val id = pizzaJson.getString("id")
                    val name = pizzaJson.getString("name")
                    val image = pizzaJson.getString("img_url")
                    val price = pizzaJson.getString("price")
                    val pizza = PizzaModel(id, name, image, price)
                    allPizza.add(pizza)
                }
                // Appeler le callback avec les données chargées
                callback.onDataLoaded(allPizza)
            },
            {
                // En cas d'erreur, appeler le callback avec un message d'erreur
                callback.onError(it.message ?: "Unknown error occurred")
            }
        )
        queue.add(jsonArrayRequest)
    }
}
