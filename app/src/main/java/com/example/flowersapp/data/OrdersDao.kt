package com.example.flowersapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flowersapp.models.Order

@Dao
interface OrdersDao {
    @Query("UPDATE orders SET description = :description, price = :price, deliver_to = :deliverTo WHERE orderID = :orderID")
    suspend fun updateOrder(orderID: String, description: String?, price: Double?, deliverTo: String?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrder(order: Order): Long

    @Query("UPDATE orders SET status = :status WHERE orderID = :orderID")
    suspend fun updateOrderStatus(orderID: String, status: String)

    @Query("Select * from orders order by orderID ASC")
    fun getOrders(): LiveData<List<Order>>

    @Query("Select * from orders where orderID = :orderID")
    fun getOrderByID(orderID: String): LiveData<Order>
}