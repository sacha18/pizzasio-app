import android.app.Application
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.pizzasio.data.model.commande.CommandeModel
import com.example.pizzasio.data.model.commande.LigneCommande
import com.example.pizzasio.ui.Pizzasio

class CommandeViewModel {

    interface CommandeCallback {
        fun onDataLoaded(pizzaList: List<CommandeModel>)
        fun onError(message: String)
    }

    fun loadCommande(application: Application, callback: CommandeCallback) {
        val allCommande = mutableListOf<CommandeModel>()

        // Créer une file de requêtes Volley
        val idUser = Pizzasio.user.id
        val queue = Volley.newRequestQueue(application)
        val apiUrl = "https://slam.cipecma.net/2224/svilletard/Api/Commande?id=$idUser"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, apiUrl, null,
            { response ->
                Log.d("TAG", response.toString())
                for (i in 0 until response.length()) {
                    val pizzaJson = response.getJSONObject(i)
                    val commandeInfo = pizzaJson.getJSONObject("commande")
                    val ligneCommandeArray = pizzaJson.getJSONArray("ligne_commande")
                    val id = commandeInfo.getString("id_commande")
                    val dateCommande = commandeInfo.getString("date_commande")
                    val totalCommande = commandeInfo.getString("total_commande")
                    val ligneCommandeList = mutableListOf<LigneCommande>() // Déplacer la déclaration à l'intérieur de la boucle

                    for (j in 0 until ligneCommandeArray.length()) {
                        val ligneCommandeJson = ligneCommandeArray.getJSONObject(j)
                        val size = ligneCommandeJson.getString("size")
                        val priceCommande = ligneCommandeJson.getString("price_commande")
                        val name = ligneCommandeJson.getString("name")

                        val ligneCommande = LigneCommande(name = name, price = priceCommande, size = size)
                        ligneCommandeList.add(ligneCommande)
                    }
                    val commande = CommandeModel(id = id, date = dateCommande, price = totalCommande, ligneCommande = ligneCommandeList)
                    allCommande.add(commande)
                    allCommande.reverse()
                }
                // Appeler le callback avec les données chargées
                callback.onDataLoaded(allCommande)
            },
            {
                // En cas d'erreur, appeler le callback avec un message d'erreur
                callback.onError(it.message ?: "Unknown error occurred")
            }
        )
        queue.add(jsonArrayRequest)
    }
}
