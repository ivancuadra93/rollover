package com.ijsbss.rollover.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ijsbss.rollover.data.entities.Category
import com.ijsbss.rollover.data.entities.Expense

data class CategoryWithExpenses(
        @Embedded
        val category: Category,

        @Relation(
            parentColumn = "category_id",
            entityColumn = "category_id"
        )
        val expenses: MutableList<Expense>
)