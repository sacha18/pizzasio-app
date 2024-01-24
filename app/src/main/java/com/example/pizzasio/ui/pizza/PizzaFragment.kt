package com.example.pizzasio.ui.pizza

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pizzasio.R
import com.example.pizzasio.databinding.FragmentPizzaBinding

class PizzaFragment : Fragment() {

    private var _binding: FragmentPizzaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPizzaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pizzaList = listOf("Pizza Margherita \n5€", "Pizza Pepperoni \n12€", "Pizza Quattro Formaggi\n15€")


        val listView: ListView = root.findViewById(R.id.pizza_list)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, pizzaList)

        listView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
