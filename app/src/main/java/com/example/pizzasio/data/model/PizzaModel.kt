package com.example.pizzasio.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PizzaDto (
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)