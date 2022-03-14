package com.example.flowersapp.views.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.flowersapp.R
import com.example.flowersapp.adapters.OrderListAdapter
import com.example.flowersapp.databinding.FragmentOrderlistBinding
import com.example.flowersapp.factory.ViewModelFactory
import com.example.flowersapp.models.Order
import com.example.flowersapp.models.Status
import com.example.flowersapp.utils.InternetUtils
import com.example.flowersapp.utils.Navigator
import com.example.flowersapp.utils.ToastMessage
import com.example.flowersapp.viewmodel.DBOrdersViewModel
import com.example.flowersapp.viewmodel.OrdersViewModel

class OrderListFragment : Fragment(), OrderListAdapter.OnItemClickListener {

    companion object {
        private val TAG: String? = OrderListFragment::class.java.canonicalName

        fun newInstance(): OrderListFragment {
            return OrderListFragment()
        }
    }

    private val navigator = Navigator()
    private var binding: FragmentOrderlistBinding? = null
    private var adapter = OrderListAdapter(this)
    private var orders = mutableListOf<Order>()

    private val ordersViewModel: OrdersViewModel by viewModels {
        ViewModelFactory()
    }

    private val dbOrdersViewModel: DBOrdersViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderlistBinding.inflate(layoutInflater)
        binding?.orderListRecycleView?.adapter = adapter
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderListUpdateObserver()

        binding?.searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.filter.filter(binding?.searchEditText?.text)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                if (InternetUtils.isInternetConnection(context)) {
                    ordersViewModel.getOrders()
                    ToastMessage.showMessage(getString(R.string.DATA_UPDATED))
                } else {
                    ToastMessage.showMessage(getString(R.string.NO_INTERNET))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(order: Order) {
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.PASSED_ORDER), order)
        val orderListFragment = OrderDetailedViewFragment.newInstance()
        orderListFragment.arguments = bundle
        navigator.replaceFragment(orderListFragment, activity)
    }

    private fun orderListUpdateObserver() {
        if (InternetUtils.isInternetConnection(context)) {
            Log.d(TAG, getString(R.string.BACKEND_DATA))
            ordersViewModel.getOrders()
        }

        ordersViewModel.orders.observe(viewLifecycleOwner) {
            Log.d(TAG, getString(R.string.ORDER_LIST_UPDATED) + it + " size " + it.size)
            orders.clear()
            orders.addAll(it)
            adapter.setOrderList(orders)
            dbOrdersViewModel.updateOrders(orders)
        }

        dbOrdersViewModel.getOrders().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                if (!InternetUtils.isInternetConnection(context)) {
                    adapter.setOrderList(it)
                    Log.d(TAG, getString(R.string.DB_DATA))
                    Toast.makeText(context, getString(R.string.DB_DATA), Toast.LENGTH_SHORT).show()
                } else {
                    for (order in orders) {
                        val dbOrder = it.find { item -> item.orderID == order.orderID }
                        order.status = dbOrder?.status ?: Status.New
                    }
                    adapter.setOrderList(orders)
                }
            }
        }
    }
}