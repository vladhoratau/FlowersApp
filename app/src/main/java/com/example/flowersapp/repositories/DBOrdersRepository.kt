package com.example.flowersapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.flowersapp.R
import com.example.flowersapp.data.OrdersDao
import com.example.flowersapp.models.Order
import com.example.flowersapp.models.Status
import com.example.flowersapp.utils.ApplicationClass

class DBOrdersRepository(private val ordersDao: OrdersDao) {

    companion object {
        private val TAG: String? = DBOrdersRepository::class.java.canonicalName
    }

    val orderList: LiveData<List<Order>> = ordersDao.getOrders()

    suspend fun updateOrderStatus(orderID: String, status: Status) {
        ordersDao.updateOrderStatus(orderID, status.toString())
    }

    suspend fun updateOrders(orders: List<Order>) {
        for (order in orders) {
            this.updateOrder(order)
        }
    }

    private suspend fun updateOrder(order: Order) {
        val result = ordersDao.insertOrder(order)
        if (result == -1L) {
            ordersDao.updateOrder(order.orderID, order.description, order.price, order.deliverTo)
        }
        Log.d(TAG, ApplicationClass.instance.getString(R.string.UPDATE_ORDERS))
    }
}