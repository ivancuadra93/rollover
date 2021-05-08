package com.ijsbss.rollover.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ijsbss.rollover.data.Relations.CategoryWithExpenses
import com.ijsbss.rollover.data.entities.Category
import com.ijsbss.rollover.data.entities.Expense

class CategoryRepository(private val dao: CategoryDao) {
    val categories = dao.getCategories()

    fun categoryWithExpenses(categoryId: Int) : LiveData<MutableList<CategoryWithExpenses>> {
        return dao.getCategoryWithExpenses(categoryId)
    }

    suspend fun updateTotalSpentToZero(categoryId: Int) {
        dao.updateTotalSpentToZero(categoryId, 0.0F)
    }

    suspend fun insert(category: Category){
        dao.insert(category)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

    suspend fun delete(categoryId: Int) {
        dao.delete(categoryId)
        dao.deleteExpense(categoryId)
    }
}