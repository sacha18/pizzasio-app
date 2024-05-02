package com.example.pizzasio.ui.commande

import CommandeViewModel
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.pizzasio.data.model.commande.CommandeModel
import com.example.pizzasio.data.model.commande.LigneCommande
import com.example.pizzasio.databinding.FragmentPizzaBinding
import com.example.pizzasio.ui.theme.PizzaTheme

class CommandeFragment : Fragment() {

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
fun LoadCommandeData(application: Application) {
    val commandeListState = remember { mutableStateOf<List<CommandeModel>>(emptyList()) }
    val errorMessageState = remember { mutableStateOf<String?>(null) }

    // Appel de la fonction loadPizza avec le callback pour récupérer les données
    CommandeViewModel().loadCommande(application, object : CommandeViewModel.CommandeCallback {
        override fun onDataLoaded(commandeList: List<CommandeModel>) {
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
fun CommandeList(commandeList: List<CommandeModel>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(commandeList) { commande ->
            CommandeCard(
                commande = commande,
                modifier = Modifier.padding(8.dp),
                context = LocalContext.current
            )
        }
    }
}

@Composable
fun CommandeCard(commande: CommandeModel, modifier: Modifier, context: Context) {
    ElevatedCard(
        modifier = modifier.padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Commande n°${commande.id}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
                Text(
                    text= "le " + commande.date,
                    style = MaterialTheme.typography.bodySmall
                )
            Spacer(modifier = Modifier.height(8.dp))
            RenderLigneCommandeItem(commande.ligneCommande)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Prix total : " + commande.price + '€',
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun RenderLigneCommandeItem(ligneCommandeList: List<LigneCommande>) {
    ligneCommandeList.forEach() {
        Text(
            text = "- " + it.name + " " + it.size.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}


