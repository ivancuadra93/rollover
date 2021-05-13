package com.ijsbss.rollover.data.db

import androidx.lifecycle.LiveData
import com.ijsbss.rollover.data.relations.CategoryWithExpenses
import com.ijsbss.rollover.data.entities.Category

class CategoryRepository(private val dao: CategoryDao) {
    val categories = dao.getCategories()

    fun categoryWithExpenses(categoryId: Int) : LiveData<MutableList<CategoryWithExpenses>> {
        return dao.getCategoryWithExpenses(categoryId)
    }

    suspend fun updateTotalSpentToZero(categoryId: Int) {
        dao.updateTotalSpentToZero(categoryId, 0.0F)
    }

    suspend fun updateCategoryId(categoryId: Int){
        dao.updateCategoryId(categoryId)
    }

    suspend fun insert(category: Category){
        dao.insert(category)
    }

    suspend fun deleteCategoryAndExpense(categoryId: Int) {
        dao.deleteCategory(categoryId)
        dao.deleteExpense(categoryId)
    }

    suspend fun deleteCategoryOnly(categoryId: Int){
        dao.deleteCategory(categoryId)
    }
}