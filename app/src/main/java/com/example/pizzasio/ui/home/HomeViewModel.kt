package com.example.pizzasio.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.Request


class HomeViewModel : ViewModel() {

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://192.168.80.40/pizza_git/pizzasio/public/Api/Login?email=admin@pizza&password=pizza")
        .addHeader("Cookie", "ci_session=336anshjf6od9vkr53cjlnfv9nto6kqs")
        .build()
    val response = client.newCall(request).execute()
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragmeeenteee"
    }

    val text: LiveData<String> = _text
}