package com.example.pizzasio.ui.pizza

import PizzaViewModel
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter
import com.example.pizzasio.data.model.pizza.PizzaModel
import com.example.pizzasio.databinding.FragmentPizzaBinding
import com.example.pizzasio.ui.panier.PanierViewModel
import com.example.pizzasio.ui.theme.PizzaTheme


val panierViewModel = PanierViewModel()
class PizzaFragment : Fragment() {

    private var _binding: FragmentPizzaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)


        _binding = FragmentPizzaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return ComposeView(requireContext()).apply {
            setContent {
                PizzaTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        PizzaApp()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


@Composable
fun PizzaCard(pizza: PizzaModel, modifier: Modifier = Modifier, context: Context) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ), modifier = modifier
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(pizza.image),
                contentDescription = pizza.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = pizza.name,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = pizza.price,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(
                    onClick = { panierViewModel.addPizzaToPanier(pizza)
                              Toast.makeText(context, "Pizza ajoutée", Toast.LENGTH_SHORT).show()},
                    modifier = Modifier
                        .padding(horizontal = 8.dp)

                ) {
                    Icon(Icons.Outlined.AddCircle, contentDescription = "Mettre au panier", modifier = Modifier.size(400.dp))
                }
            }

        }
    }
}

@Composable
fun PizzaList(pizzaList: List<PizzaModel>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(pizzaList) { pizza ->
            PizzaCard(
                pizza = pizza, modifier = Modifier.padding(8.dp), context = LocalContext.current
            )
        }
    }
}

@Composable
fun PizzaApp() {
    val application = LocalContext.current.applicationContext as Application
    // Appel de la fonction loadPizza avec le callback pour récupérer les données
    LoadPizzaData(application = application)
}

@Composable
fun LoadPizzaData(application: Application) {
    val pizzaListState = remember { mutableStateOf<List<PizzaModel>>(emptyList()) }
    val errorMessageState = remember { mutableStateOf<String?>(null) }

    // Appel de la fonction loadPizza avec le callback pour récupérer les données
    PizzaViewModel().loadPizza(application, object : PizzaViewModel.PizzaCallback {
        override fun onDataLoaded(pizzaList: List<PizzaModel>) {
            pizzaListState.value = pizzaList
        }

        override fun onError(message: String) {
            errorMessageState.value = message
        }
    })

    // Affichage de la liste de pizzas ou de l'erreur
    if (errorMessageState.value != null) {
        // Gérer l'erreur ici
    } else {
        PizzaList(pizzaList = pizzaListState.value)
    }
}


//@Preview
//@Composable
//private fun AffirmationCardPreview() {
//    PizzaCard(
//        PizzaModel(
//            "R.string.affirmation1",
//            "https://slam.cipecma.net/assets/media/logos/PizzaSioFullLogoWhite.svg",
//            "0.00"
//        )
//    )
//}
}
