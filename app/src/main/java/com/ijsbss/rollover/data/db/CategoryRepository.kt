package com.ijsbss.rollover.data.db

import com.ijsbss.rollover.data.entities.Category

class CategoryRepository(private val dao: CategoryDao) {
    val categories = dao.getCategories()

    suspend fun insert(category: Category){
        dao.insert(category)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}