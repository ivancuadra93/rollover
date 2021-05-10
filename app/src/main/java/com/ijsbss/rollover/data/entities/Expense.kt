package com.ijsbss.rollover.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "expense_id")
        val expenseId: Int,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "amount")
        val amount: Float,

        @ColumnInfo(name = "accountOptions")
        val accountOptions: Int,

        @ColumnInfo(name = "date")
        val date: String,

        @ColumnInfo(name = "view_order")
        val viewOrder: Byte,

        @ColumnInfo(name = "category_id")
        val categoryId: Int
)