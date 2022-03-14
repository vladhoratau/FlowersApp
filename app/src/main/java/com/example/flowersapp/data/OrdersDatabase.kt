package com.example.flowersapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flowersapp.models.Order

@Database(entities = [Order::class], version = 1, exportSchema = false)

abstract class OrdersDatabase : RoomDatabase() {

    abstract fun getOrdersDao(): OrdersDao

    companion object {
        @Volatile
        private var INSTANCE: OrdersDatabase? = null

        fun getDatabase(context: Context): OrdersDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrdersDatabase::class.java,
                    "orders"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}