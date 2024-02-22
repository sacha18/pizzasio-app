package com.example.pizzasio.ui.panier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.pizzasio.data.model.panier.getAllCartItemsAsList
import com.example.pizzasio.ui.Pizzasio.Companion.panier
import com.example.pizzasio.ui.theme.PizzaTheme

class PanierFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PizzaTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        PanierContent()
                    }
                }
            }
        }
    }

    @Composable
    fun PanierContent() {
        val panierValue =
            panier.getAllCartItemsAsList()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                panierValue.forEach { panier ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), // Ajout du padding horizontal
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = panier.name + " - " + panier.price + "€",
                            lineHeight = 15.sp
                        )
                        IconButton(
                            onClick = {
                                Toast.makeText(context, "Pizza supprimée", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxHeight()

                        ) {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = "Supprimer la pizza du panier",
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )            ) {
                Text("Commander")
            }

        }
    }




}
