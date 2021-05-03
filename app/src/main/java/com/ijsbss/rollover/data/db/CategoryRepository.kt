package com.ijsbss.rollover.data.db

import androidx.lifecycle.LiveData
import com.ijsbss.rollover.data.Relations.CategoryWithExpenses
import com.ijsbss.rollover.data.entities.Category

class CategoryRepository(private val dao: CategoryDao) {
    val categories = dao.getCategories()
    fun categoryWithExpenses(categoryId: Int) : LiveData<MutableList<CategoryWithExpenses>> {
        return dao.getCategoryWithExpenses(categoryId)
    }

    suspend fun insert(category: Category){
        dao.insert(category)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

    suspend fun delete(categoryID: Int) {
        dao.delete(categoryID)
        dao.deleteExpense(categoryID)
    }
}