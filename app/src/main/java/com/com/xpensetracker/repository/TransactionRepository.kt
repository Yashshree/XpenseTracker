package com.com.xpensetracker.repository

import androidx.lifecycle.LiveData
import com.com.xpensetracker.model.Transaction
import com.com.xpensetracker.room.AppDatabase
import javax.inject.Inject

class TransactionRepository @Inject constructor(var roomDatabase: AppDatabase) {
    val transactionList : LiveData<List<Transaction>> = roomDatabase.transactionDao().getTransactionList()

    val incomeAmountList : LiveData<List<Int>> = roomDatabase.transactionDao().getIncomeAmountList()
    val expenseAmountList : LiveData<List<Int>> = roomDatabase.transactionDao().getExpenseAmountList()
    val investmentAmountList : LiveData<List<Int>> = roomDatabase.transactionDao().getInvestmentAmountList()

    suspend fun updateTransaction(transaction:Transaction){
        roomDatabase.transactionDao().insertTransaction(transaction)
    }

}