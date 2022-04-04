package com.com.xpensetracker.repository

import android.util.Log
import androidx.room.RoomDatabase
import com.com.xpensetracker.model.Category
import com.com.xpensetracker.model.Transaction
import com.com.xpensetracker.room.AppDatabase
import javax.inject.Inject

class AddCategoryRepository @Inject constructor(var roomDatabase: AppDatabase) {

    suspend fun insertAllCategories(categories: List<Category>) {
       val tt= roomDatabase.categoryDao().insertAllCategory(categories)
        Log.e("insertion operation",tt.toString())
    }


    suspend fun getAllCategories(): List<Category> =
        roomDatabase.categoryDao().getAllCategories()

    suspend fun addTransaction(transaction:Transaction) = roomDatabase.transactionDao().insertTransaction(transaction)



}
