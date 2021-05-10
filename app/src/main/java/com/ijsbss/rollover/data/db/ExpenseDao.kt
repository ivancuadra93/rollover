package com.ijsbss.rollover.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ijsbss.rollover.data.entities.Expense

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY view_order")
    fun getExpenses(): LiveData<MutableList<Expense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)

    @Query("UPDATE CATEGORIES SET total_spent =:amount WHERE category_id = :categoryId")
    suspend fun updateTotalSpent(categoryId: Int, amount: Float)

    @Query("DELETE FROM expenses")
    suspend fun deleteAll()

    @Query("DELETE FROM expenses WHERE expense_id LIKE :expenseID")
    suspend fun delete(expenseID: Int)
}