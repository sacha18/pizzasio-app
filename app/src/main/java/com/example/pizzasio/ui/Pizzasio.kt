package com.example.pizzasio.ui

import android.app.Application
import com.example.pizzasio.data.model.panier.PanierModel
import com.example.pizzasio.data.model.user.User

class Pizzasio() : Application() {
    companion object {
        var test: String = "default"

        var user : User = User("0")
        var panier: PanierModel = PanierModel()
    }
}