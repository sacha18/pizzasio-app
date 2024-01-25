package com.example.pizzasio.data

import com.example.pizzasio.R
import com.example.pizzasio.data.model.PizzaDto

class PizzaDataSource {
    fun loadAffirmations(): List<PizzaDto> {
        return listOf<PizzaDto>(
            PizzaDto(R.string.affirmation1, R.drawable.image1),
            PizzaDto(R.string.affirmation2, R.drawable.image2),
            PizzaDto(R.string.affirmation3, R.drawable.image3),
            PizzaDto(R.string.affirmation4, R.drawable.image4),
            PizzaDto(R.string.affirmation5, R.drawable.image5),
            PizzaDto(R.string.affirmation6, R.drawable.image6),
            PizzaDto(R.string.affirmation7, R.drawable.image7),
            PizzaDto(R.string.affirmation8, R.drawable.image8),
            PizzaDto(R.string.affirmation9, R.drawable.image9),
            PizzaDto(R.string.affirmation10, R.drawable.image10))
    }
}