package com.example.beardie.currencyholder.ui.finance

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.model.Transaction

class TransactionAdapter(
        private val context: Context?,
        private val transactions : List<Transaction>?
) : RecyclerView.Adapter<TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TransactionViewHolder {
        return TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.transaction_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return transactions?.size ?: 0
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, pos: Int) {
            holder.amount.text = transactions?.get(pos)?.count.toString()
            holder.currency.text = transactions?.get(pos)?.currency?.shortTitle

    }

}