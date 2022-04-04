package com.com.xpensetracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.com.xpensetracker.utils.ExpenseCategoryType

@Entity
data class Category(@PrimaryKey (autoGenerate = true) var categoryId:Long ?=null,
               var image:Int, var title:String, var type:ExpenseCategoryType, var color:Int,
                    var rippleBackground:Int, var isSelected:Boolean=false)