package com.example.flowersapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flowersapp.R
import com.example.flowersapp.models.Order
import com.example.flowersapp.repositories.OrdersRepository
import com.example.flowersapp.services.OrdersService
import com.example.flowersapp.utils.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersViewModel : ViewModel() {

    companion object {
        private val TAG: String? = OrdersViewModel::class.java.canonicalName
    }

    private val ordersRepository: OrdersRepository = OrdersRepository(OrdersService.getInstance())
    val orders = MutableLiveData<List<Order>>()

    fun getOrders() {
        val response = ordersRepository.getOrders()
        response?.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                orders.postValue(response.body())
                Log.d(TAG, ApplicationClass.instance.getString(R.string.GET_REQUEST_SUCCESSFUL))
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                t.message?.let { Log.d(TAG, it) }
            }
        })
    }

}