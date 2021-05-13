package com.ijsbss.rollover.data.db

import com.ijsbss.rollover.data.entities.Category
import androidx.lifecycle.LiveData
import androidx.room.*
import com.ijsbss.rollover.data.relations.CategoryWithExpenses

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY view_order")
    fun getCategories(): LiveData<MutableList<Category>>

    @Transaction
    @Query("SELECT * FROM categories WHERE category_id = :categoryID")
    fun getCategoryWithExpenses(categoryID: Int) : LiveData<MutableList<CategoryWithExpenses>>

    @Transaction
    @Query("SELECT date FROM expenses WHERE category_id = :categoryId")
    fun getDate(categoryId: Int) : LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Query("UPDATE CATEGORIES SET total_spent =:amount WHERE category_id = :categoryId")
    suspend fun updateTotalSpentToZero(categoryId: Int, amount: Float)

    @Query("UPDATE CATEGORIES SET category_id = :categoryId WHERE category_id = (SELECT MAX(category_id) FROM CATEGORIES)")
    suspend fun updateCategoryId(categoryId: Int)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()

    @Query("DELETE FROM categories WHERE category_id LIKE :categoryID")
    suspend fun deleteCategory(categoryID: Int)

    @Query("DELETE FROM expenses WHERE category_id LIKE :categoryID")
    suspend fun deleteExpense(categoryID: Int)
}