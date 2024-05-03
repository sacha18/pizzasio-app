package com.example.pizzasio.ui.panier

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.pizzasio.R
import com.example.pizzasio.ui.theme.PizzaTheme

val panierViewModel = PanierViewModel()

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
                        PanierContent(context = LocalContext.current)
                    }
                }
            }
        }
    }

    @Composable
    fun PanierContent(context: Context) {
        val panierValue = panierViewModel.panierItemsState.value
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                panierValue.forEach { panierItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), // Ajout du padding horizontal
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = panierItem.name + " - " + panierItem.price + "€" + " " + panierItem.size,
                            lineHeight = 15.sp
                        )
                        Row {
                            IconButton(
                                onClick = {
                                    panierViewModel.removePizzaFromPanier(panierItem)

                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxHeight()

                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_minus),
                                    contentDescription = "Supprimer la pizza du panier",
                                )
                            }
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
                onClick = {
                    panierViewModel.makeCommand(context = context, panierValue)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text("Commander")
            }
        }
    }


}
