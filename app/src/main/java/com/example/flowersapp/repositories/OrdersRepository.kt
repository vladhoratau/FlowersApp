package com.example.flowersapp.repositories

import com.example.flowersapp.services.OrdersService

class OrdersRepository(private val ordersService: OrdersService?) {

    fun getOrders() = ordersService?.getOrders()
}