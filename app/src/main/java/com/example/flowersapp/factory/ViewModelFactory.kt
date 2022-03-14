package com.example.flowersapp.factory

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flowersapp.R
import com.example.flowersapp.utils.ApplicationClass
import com.example.flowersapp.viewmodel.DBOrdersViewModel
import com.example.flowersapp.viewmodel.OrdersViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(OrdersViewModel::class.java) -> {
                OrdersViewModel() as T
            }
            modelClass.isAssignableFrom(DBOrdersViewModel::class.java) -> {
                DBOrdersViewModel(ApplicationClass.instance) as T
            }
            else -> {
                throw IllegalAccessException(
                    Resources.getSystem().getString(R.string.MODEL_CLASS_ERROR)
                )
            }
        }
    }
}