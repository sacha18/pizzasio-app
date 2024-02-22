package com.example.pizzasio.ui.pizza

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pizzasio.databinding.FragmentPizzaBinding

class PizzaFragment : Fragment() {

    private var _binding: FragmentPizzaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pizzaViewModel =
            ViewModelProvider(this).get(PizzaViewModel::class.java)
        // https://slam.cipecma.net/jsabbah/Api/AllPizza
        val allPiza = arr
        _binding = FragmentPizzaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: ListView = binding.pizzaList
        pizzaViewModel.text.observe(viewLifecycleOwner) {
            listView.set
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
