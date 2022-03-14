package com.example.flowersapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.flowersapp.R
import com.example.flowersapp.databinding.FragmentOrderDetailedViewBinding
import com.example.flowersapp.factory.ViewModelFactory
import com.example.flowersapp.models.Order
import com.example.flowersapp.models.Status
import com.example.flowersapp.utils.Navigator
import com.example.flowersapp.viewmodel.DBOrdersViewModel
import com.google.android.material.textview.MaterialTextView

class OrderDetailedViewFragment : Fragment() {

    companion object {
        private val TAG: String = OrderDetailedViewFragment::class.java.canonicalName

        fun newInstance(): OrderDetailedViewFragment {
            return OrderDetailedViewFragment()
        }
    }

    private var binding: FragmentOrderDetailedViewBinding? = null
    private val dbOrdersViewModel: DBOrdersViewModel by viewModels {
        ViewModelFactory()
    }
    private lateinit var order: Order
    private val navigator = Navigator()


    override fun onCreate(savedInstanceState: Bundle?) {
        if (arguments != null) {
            order = arguments?.getSerializable(getString(R.string.PASSED_ORDER)) as Order
        }
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailedViewBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detailed_view_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateTextView(
            getString(R.string.ORDER_ID_TEXT) + order.orderID,
            binding?.orderID
        )
        updateTextView(
            getString(R.string.ORDER_DESCRIPTION_TEXT) + order.description,
            binding?.orderDescription
        )
        updateTextView(
            getString(R.string.ORDER_PRICE_TEXT) + order.price.toString() + getString(R.string.CURRENCY),
            binding?.orderPrice
        )
        updateTextView(
            getString(R.string.ORDER_DELIVER_TO_TEXT) + order.deliverTo,
            binding?.deliverTo
        )
        updateTextView(
            getString(R.string.ORDER_STATUS_TEXT) + order.status,
            binding?.status
        )

        updateButtonText()

        binding?.changeStatusButton?.setOnClickListener {
            updateOrderStatus()
            updateButtonText()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.back -> {
                navigator.replaceFragment(OrderListFragment.newInstance(), this.activity)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateOrderStatus() {
        when (order.status) {
            Status.New -> order.status = Status.Pending
            Status.Pending -> order.status = Status.Delivered
            else -> Status.New
        }
        order.status?.let { dbOrdersViewModel.updateOrderStatus(order.orderID, it) }
        updateTextView(
            getString(R.string.ORDER_STATUS_TEXT) + order.status,
            binding?.status
        )
        Log.d(TAG, getString(R.string.STATUS_UPDATED))
    }

    private fun updateButtonText() {
        when (order.status) {
            Status.New -> {
                binding?.changeStatusButton?.text = getString(R.string.PENDING_ORDER)
            }
            Status.Pending -> {
                binding?.changeStatusButton?.text = getString(R.string.DELIVERED_ORDER)
            }
            else -> {
                binding?.changeStatusButton?.visibility = View.GONE
            }
        }
    }

    private fun updateTextView(text: String, view: MaterialTextView?) {
        view?.text = text
    }
}

