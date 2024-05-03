package com.example.pizzasio.data.model.pizza
data class PizzaModel(
     val id: String,
     val name: String,
     val image: String,
     val price: String,
     val size: String,
     val ingredients: List<IngredientModel>
)

data class IngredientModel(
     val id: String,
     val name: String,
     val count: Int
)