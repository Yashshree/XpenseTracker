package com.com.xpensetracker.interfaces

import com.com.xpensetracker.model.Category
import com.com.xpensetracker.model.Transaction

interface OnListItemClickListener {
    fun onListItemClick(category: Category)
}