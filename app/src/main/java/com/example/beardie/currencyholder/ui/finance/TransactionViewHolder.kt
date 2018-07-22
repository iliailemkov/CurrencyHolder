package com.example.beardie.currencyholder.ui.finance

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.transaction_list_item.view.*

class TransactionViewHolder(view : View): RecyclerView.ViewHolder(view) {
    val amount = view.tv_amount
    val currency = view.tv_currency
}