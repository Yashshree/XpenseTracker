package com.com.xpensetracker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.com.xpensetracker.model.Category

@Dao
interface CategoryDao {

    @Insert
    fun insertAllCategory(categories:List<Category>):List<Long>

    @Query("SELECT * FROM Category")
    fun getAllCategories(): List<Category>
}