package com.example.flowersapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "orders")
class Order : Serializable {

    @PrimaryKey
    @ColumnInfo(name = "orderID")
    @SerializedName("id")
    lateinit var orderID: String

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String? = null

    @ColumnInfo(name = "price")
    @SerializedName("price")
    var price: Double? = null

    @ColumnInfo(name = "deliver_to")
    @SerializedName("deliver_to")
    var deliverTo: String? = null

    @ColumnInfo(name = "status")
    var status: Status? = Status.New
}