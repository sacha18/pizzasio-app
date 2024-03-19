package com.example.pizzasio.ui

import android.app.Application
import com.example.pizzasio.data.model.panier.PanierModel

class Pizzasio() : Application() {
    companion object {
        var test: String = "default"

        //        var user : User = User()
        var panier: PanierModel = PanierModel()
    }
}