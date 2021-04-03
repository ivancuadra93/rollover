package com.ijsbss.rollover.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ijsbss.rollover.data.entities.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY view_order")
    fun getCategories(): MutableList<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: MutableList<Category>)
}