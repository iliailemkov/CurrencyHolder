package com.example.beardie.currencyholder.domain

import com.example.beardie.currencyholder.data.BalanceRepository
import com.example.beardie.currencyholder.data.ExchangeRepository
import com.example.beardie.currencyholder.data.HardcodeValues
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.data.enum.TypeCategoryEnum
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.data.model.TransactionCategory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class BalanceInteractor @Inject constructor(
        private val balanceRepository: BalanceRepository,
        private val transactionRepository: TransactionRepository,
        private val exchangeRepository: ExchangeRepository
) {

    fun addTransactions(amount: Double, balance: Balance, currency: FinanceCurrency, date: Date, category: TransactionCategory) {
        val coef = if(category.type == TypeCategoryEnum.OUTGO) -1 else 1
        if(balance.currency != currency) {
            exchangeRepository.getExchangeRate(currency, balance.currency, object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    transactionRepository.add(Transaction(UUID.randomUUID().toString(),
                            coef * amount * response.body()?.string().toString().substringAfterLast(":").dropLast(2).toDouble(),
                            balance,
                            balance.currency,
                            date,
                            category))
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }
            })
        } else {
            transactionRepository.add(Transaction(UUID.randomUUID().toString(),
                    coef * amount,
                    balance,
                    currency,
                    date,
                    category))
        }

    }

    private fun calculateBalance(balance: Balance, amount: Double) {
        balanceRepository.setBalance(balance, amount)
    }

    fun getSumTransaction(defaultCurrency: FinanceCurrency) : Double {
        return HardcodeValues.transactions.sumByDouble { t ->
            if(t.currency ==  defaultCurrency)
                t.count
            else
                t.count * HardcodeValues.exchange.find { exp -> exp.fromCurrency == t.currency }?.coef!!
        }
    }
}