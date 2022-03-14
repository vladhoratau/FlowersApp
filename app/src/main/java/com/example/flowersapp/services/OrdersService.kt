package com.example.flowersapp.services

import com.example.flowersapp.R
import com.example.flowersapp.models.Order
import com.example.flowersapp.utils.ApplicationClass
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface OrdersService {

    @GET("orders")
    fun getOrders(): Call<List<Order>>

    companion object {
        private val BASE_URL = ApplicationClass.instance.getString(R.string.BASE_URL)
        private var ordersService: OrdersService? = null

        fun getInstance(): OrdersService? {
            if (ordersService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                ordersService = retrofit.create(OrdersService::class.java)
            }
            return ordersService
        }
    }
}