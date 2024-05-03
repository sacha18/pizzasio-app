import android.app.Application
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.pizzasio.data.model.pizza.PizzaModel
import com.example.pizzasio.data.model.pizza.IngredientModel

class PizzaViewModel {

    interface PizzaCallback {
        fun onDataLoaded(pizzaList: List<PizzaModel>)
        fun onError(message: String)
    }

    fun loadPizza(application: Application, callback: PizzaCallback) {
        val allPizza = mutableListOf<PizzaModel>()

        // Create a Volley request queue
        val queue = Volley.newRequestQueue(application)
        val apiUrl = "https://slam.cipecma.net/2224/svilletard/Api/AllPizza"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, apiUrl, null,
            { response ->
                for (i in 0 until response.length()) {
                    val pizzaJson = response.getJSONObject(i)
                    val id = pizzaJson.getString("id")
                    val name = pizzaJson.getString("name")
                    val image = if (pizzaJson.has("img_url")) {
                        pizzaJson.getString("img_url")
                    } else {
                        "https://img.freepik.com/photos-gratuite/pizza-pizza-remplie-tomates-salami-olives_140725-1200.jpg?t=st=1714719871~exp=1714723471~hmac=69b8b4a99a0835dfea897e94448aaa3379c7b018d0ed3613e4fd1ff04c4c08fe&w=826"
                    }
                    val price = pizzaJson.getString("price")

                    // Retrieve ingredients for the pizza
                    val ingredientsJson = pizzaJson.getJSONArray("ingredients")
                    val ingredients = mutableListOf<IngredientModel>()
                    for (j in 0 until ingredientsJson.length()) {
                        val ingredientJson = ingredientsJson.getJSONObject(j)
                        val ingredientId = ingredientJson.getString("id")
                        val ingredientName = ingredientJson.getString("name")
                        val ingredientCount = ingredientJson.getInt("count")
                        val ingredient = IngredientModel(ingredientId, ingredientName, ingredientCount)
                        ingredients.add(ingredient)
                    }

                    // Create PizzaModel with ingredients
                    val pizza = PizzaModel(id, name, image, price, size = "", ingredients)
                    allPizza.add(pizza)
                }
                // Call the callback with loaded data
                callback.onDataLoaded(allPizza)
            },
            {
                // In case of error, call the callback with an error message
                callback.onError(it.message ?: "Unknown error occurred")
            }
        )
        queue.add(jsonArrayRequest)
    }
}
