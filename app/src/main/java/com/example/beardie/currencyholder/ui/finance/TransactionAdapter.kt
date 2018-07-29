package com.example.beardie.currencyholder.ui.finance

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.model.Transaction
import java.text.SimpleDateFormat

class TransactionAdapter(
        private val context: Context
) : RecyclerView.Adapter<TransactionViewHolder>() {

    var transactions : List<Transaction> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TransactionViewHolder {
        return TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.transaction_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: TransactionViewHolder, pos: Int) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

        if(transactions[pos].count >= 0) {
            holder.amount.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
            holder.amount.setText("+" + String.format("%.2f", transactions[pos].count))
        } else {
            holder.amount.text = String.format("%.2f", transactions[pos].count)
            holder.amount.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
        }
        holder.currency.text = transactions[pos].currency.shortTitle
        holder.date.text = dateFormat.format(transactions[pos].date)
        holder.group.text = transactions[pos].category.name
    }

}