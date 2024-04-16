package com.example.pizzasio.ui.commande

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pizzasio.data.model.pizza.PizzaModel
import com.example.pizzasio.databinding.FragmentPizzaBinding
import com.example.pizzasio.databinding.FragmentSlideshowBinding
import com.example.pizzasio.ui.theme.PizzaTheme

class CommandehowFragment : Fragment() {

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
                        CommandeApp()
                    }
                }
            }
        }
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@Composable
fun CommandeApp() {
    val application = LocalContext.current.applicationContext as Application
    // Appel de la fonction loadPizza avec le callback pour récupérer les données
    LoadCommandeData(application = application)
}

@Composable
fun LoadPizzaData(application: Application) {
    val pizzaListState = remember { mutableStateOf<List<PizzaModel>>(emptyList()) }
    val errorMessageState = remember { mutableStateOf<String?>(null) }

    // Appel de la fonction loadPizza avec le callback pour récupérer les données
    CommandehowViewModel().loadCommande(application, object : CommandehowViewModel.CommandeCallback {
        override fun onDataLoaded(pizzaList: List<PizzaModel>) {
            commandeListState.value = commandeList
        }

        override fun onError(message: String) {
            errorMessageState.value = message
        }
    })

    // Affichage de la liste de pizzas ou de l'erreur
    if (errorMessageState.value != null) {
        // Gérer l'erreur ici
    } else {
        CommandeList(commandeList = commandeListState.value)
    }
}

@Composable
fun CommandeList(pizzaList: List<PizzaModel>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(pizzaList) { commande ->
            CommandeCard(
                pizza = pizza, modifier = Modifier.padding(8.dp), context = LocalContext.current
            )
        }
    }
}