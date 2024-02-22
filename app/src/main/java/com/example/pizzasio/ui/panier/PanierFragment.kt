package com.example.pizzasio.ui.panier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.pizzasio.data.model.panier.getAllCartItemsAsList
import com.example.pizzasio.ui.Pizzasio.Companion.panier
import com.example.pizzasio.ui.theme.PizzaTheme

var panierViewModel = PanierViewModel()

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
        val panierText = panierViewModel.buildPanierDetails(panierValue)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Text(
                    text = panierText,
                    fontSize = 40.sp,
                    lineHeight = 45.sp
                )
            }
        }
    }
}
