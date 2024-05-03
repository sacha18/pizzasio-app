package com.example.pizzasio.ui.pizza

import PizzaViewModel
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter
import com.example.pizzasio.R
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
    fun PizzaCard(pizza: PizzaModel, modifier: Modifier = Modifier) {
        val showDialogDetailPizza = remember { mutableStateOf(false) }
        val showDialogAddToCart = remember { mutableStateOf(false) }

        Card(
            modifier = modifier,
            backgroundColor = Color.White, // Définir la couleur de fond de la carte
            elevation = 4.dp, // Modifier l'élévation selon vos préférences
            shape = MaterialTheme.shapes.medium // Définir la forme de la carte
        ) {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(pizza.image),
                    contentDescription = pizza.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Hauteur de l'image
                        .clickable { showDialogDetailPizza.value = true },
                    contentScale = ContentScale.Crop
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        text = pizza.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp) // Ajouter de la marge autour du texte
                    )
                    IconButton(
                        onClick = { showDialogAddToCart.value = true },
                        modifier = Modifier
                            .padding(16.dp) // Ajouter de la marge autour du bouton
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AddCircle, // Utiliser une icône d'ajout au panier
                            contentDescription = "Ajouter au panier",
                            tint = Color.Black // Utiliser la couleur principale du thème
                        )
                    }
                }

            }
        }

        if (showDialogDetailPizza.value) {
            PizzaDetailModal(pizza = pizza, showDialogDetailPizza = showDialogDetailPizza)
        }

        if (showDialogAddToCart.value) {
            PizzaAddToCartModal(pizza = pizza, showDialogAddToCart = showDialogAddToCart)
        }
    }

    @Composable
    fun PizzaDetailModal(pizza: PizzaModel, showDialogDetailPizza: MutableState<Boolean>) {
        AlertDialog(
            onDismissRequest = { showDialogDetailPizza.value = false },
            title = {
                Text(text = "Détails de la pizza")
            },
            confirmButton = {
                TextButton(onClick = { showDialogDetailPizza.value = false }) {
                    Text(text = "Fermer")
                }
            },
            text = {
                Column {
                    Text(text = "${pizza.name}\n")
                    for (ingredient in pizza.ingredients) {
                        Text(text = "- ${ingredient.name}${" x${ingredient.count}"}")
                    }
                    Text(text = "\nPrix: ${pizza.price}€\n")
                }
            }
        )
    }

    @Composable
    fun PizzaAddToCartModal(pizza: PizzaModel, showDialogAddToCart: MutableState<Boolean>) {
        val selectedSize = remember { mutableStateOf("M") } // Taille initiale sélectionnée

        AlertDialog(
            onDismissRequest = { showDialogAddToCart.value = false },
            title = {
                Text(text = "Ajouter au panier")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Logique pour ajouter la pizza au panier
                        panierViewModel.addPizzaToPanier(pizza, size = selectedSize.value)
                        showDialogAddToCart.value = false
                    }
                ) {
                    Text(text = "Ajouter")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialogAddToCart.value = false }) {
                    Text(text = "Annuler")
                }
            },
            text = {
                Column {
                    Text(text = "Voulez-vous ajouter ${pizza.name} au panier ?")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedSize.value == "S",
                            onClick = { selectedSize.value = "S" },
                            modifier = Modifier.size(24.dp),
                            colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
                        )
                        Text(text = "S")
                        RadioButton(
                            selected = selectedSize.value == "M",
                            onClick = { selectedSize.value = "M" },
                            modifier = Modifier.size(24.dp),
                            colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
                        )
                        Text(text = "M")
                        RadioButton(
                            selected = selectedSize.value == "L",
                            onClick = { selectedSize.value = "L" },
                            modifier = Modifier.size(24.dp),
                            colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
                        )
                        Text(text = "L")
                    }
                }
            }
        )
    }



    @Composable
    fun PizzaList(pizzaList: List<PizzaModel>, modifier: Modifier = Modifier) {
        LazyColumn(modifier = modifier) {
            items(pizzaList) { pizza ->
                PizzaCard(
                    pizza = pizza, modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

    @Composable
    fun PizzaApp() {
        val application = LocalContext.current.applicationContext as Application
        LoadPizzaData(application = application)
    }

    @Composable
    fun LoadPizzaData(application: Application) {
        val pizzaListState = remember { mutableStateOf<List<PizzaModel>>(emptyList()) }
        val errorMessageState = remember { mutableStateOf<String?>(null) }

        PizzaViewModel().loadPizza(application, object : PizzaViewModel.PizzaCallback {
            override fun onDataLoaded(pizzaList: List<PizzaModel>) {
                pizzaListState.value = pizzaList
            }

            override fun onError(message: String) {
                errorMessageState.value = message
            }
        })

        if (errorMessageState.value != null) {
            // Gérer l'erreur ici
        } else {
            PizzaList(pizzaList = pizzaListState.value)
        }
    }
}
