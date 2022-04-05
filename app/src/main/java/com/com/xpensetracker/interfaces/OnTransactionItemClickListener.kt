package com.com.xpensetracker.interfaces

import com.com.xpensetracker.model.Transaction

interface OnTransactionItemClickListener {
    fun onItemClick(transaction: Transaction)
}