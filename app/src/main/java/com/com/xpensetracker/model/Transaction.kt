package com.com.xpensetracker.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.com.xpensetracker.utils.TransactionType

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) var id: Int ?=null,
    var note: String="", var type: TransactionType,
    var expenseType:String ="",
    var amount: Int , var date: String,
    var expenseCategoryId: Long ?=null
)