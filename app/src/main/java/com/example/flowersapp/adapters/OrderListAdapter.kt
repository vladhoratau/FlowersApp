package com.example.flowersapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.flowersapp.R
import com.example.flowersapp.databinding.AdapterOrderlistBinding
import com.example.flowersapp.models.Order
import com.example.flowersapp.utils.ApplicationClass

class OrderListAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>(), Filterable {

    private var filteredOrders = mutableListOf<Order>()
    private var orders = mutableListOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterOrderlistBinding.inflate(inflater, parent, false)
        return OrderListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val order = filteredOrders[position]
        holder.binding.orderID.text = ApplicationClass.instance.getString(R.string.ORDER_ID_TEXT) + order.orderID
        holder.binding.orderStatus.text = ApplicationClass.instance.getString(R.string.ORDER_STATUS_TEXT) + order.status
    }

    override fun getItemCount(): Int {
        return filteredOrders.size
    }

    fun setOrderList(orders: List<Order>) {
        this.filteredOrders = orders.toMutableList()
        this.orders = orders.toMutableList()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(order: Order)
    }

    inner class OrderListViewHolder(val binding: AdapterOrderlistBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(filteredOrders[position])
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(sequence: CharSequence?): FilterResults {
                val filteredOrders = mutableListOf<Order>()
                if (sequence == null || sequence.isEmpty()) {
                    filteredOrders.addAll(orders)
                } else {
                    val filterPattern: String = sequence.toString().lowercase().trim()
                    for (order in orders) {
                        if (order.orderID.lowercase().contains(filterPattern)) {
                            filteredOrders.add(order)
                        }
                    }
                }
                val result = FilterResults()
                result.values = filteredOrders
                return result
            }

            override fun publishResults(sequence: CharSequence?, result: FilterResults?) {
                filteredOrders = result?.values as MutableList<Order>
                notifyDataSetChanged()
            }
        }
    }
}