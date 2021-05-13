package com.ijsbss.rollover.data.db

import com.ijsbss.rollover.data.entities.Expense


class ExpenseRepository(private val dao: ExpenseDao) {
    val expenses = dao.getExpenses()

    suspend fun insert(expense: Expense){
        dao.insert(expense)
    }

    suspend fun updateTotalSpent(categoryId: Int, amount: Float){
        dao.updateTotalSpent(categoryId, amount)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

    suspend fun delete(expenseID: Int){
        dao.delete(expenseID)
    }
}