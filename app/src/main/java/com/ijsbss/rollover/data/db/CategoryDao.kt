package com.ijsbss.rollover.data.db

import com.ijsbss.rollover.data.entities.Category
import androidx.lifecycle.LiveData
import androidx.room.*
import com.ijsbss.rollover.data.Relations.CategoryWithExpenses
import com.ijsbss.rollover.data.entities.Expense

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY view_order")
    fun getCategories(): LiveData<MutableList<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Transaction
    @Query("SELECT * FROM categories WHERE category_id = :categoryID")
    fun getCategoryWithExpenses(categoryID: Int) : LiveData<MutableList<CategoryWithExpenses>>

    @Query("DELETE FROM categories")
    suspend fun deleteAll()

    @Query("DELETE FROM categories WHERE category_id LIKE :categoryID")
    suspend fun delete(categoryID: Int)

    @Query("DELETE FROM expenses WHERE category_id LIKE :categoryID")
    suspend fun deleteExpense(categoryID: Int)

}