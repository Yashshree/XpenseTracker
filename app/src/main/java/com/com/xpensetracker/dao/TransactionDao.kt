package com.com.xpensetracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.com.xpensetracker.model.Transaction

@Dao
interface TransactionDao {
    @Insert (onConflict = REPLACE)
    fun insertTransaction(transaction:Transaction):Long

    @Query("SELECT * FROM `Transaction`")
    fun getTransactionList(): LiveData<List<Transaction>>

    @Query("SELECT amount from `Transaction` where type='INCOME'")
    fun getIncomeAmountList():LiveData<List<Int>>


    @Query("SELECT amount from `Transaction` where type='EXPENSE'")
    fun getExpenseAmountList():LiveData<List<Int>>

    @Query("SELECT amount from `Transaction` where type='SAVING_INVESTMENT'")
    fun getInvestmentAmountList():LiveData<List<Int>>


}