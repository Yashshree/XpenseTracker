package com.com.xpensetracker.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.com.xpensetracker.dao.CategoryDao
import com.com.xpensetracker.dao.TransactionDao
import com.com.xpensetracker.model.Category
import com.com.xpensetracker.model.Transaction

@Database(entities = [Category::class, Transaction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun transactionDao() : TransactionDao
}