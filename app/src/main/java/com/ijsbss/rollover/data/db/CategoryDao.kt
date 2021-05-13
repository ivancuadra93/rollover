package com.ijsbss.rollover.data.db

import com.ijsbss.rollover.data.entities.Category
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY view_order")
    fun getCategories(): LiveData<MutableList<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()

    @Query("DELETE FROM categories WHERE category_id LIKE :categoryID")
    suspend fun delete(categoryID: Int)
}