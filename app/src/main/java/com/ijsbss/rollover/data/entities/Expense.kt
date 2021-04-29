package com.ijsbss.rollover.data.entities

import android.icu.util.Calendar
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "expense_id")
        val id: Int,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "amount")
        val amount: Float,

        @ColumnInfo(name = "account")
        val account: Account,

        @ColumnInfo(name = "date")
        val date: Calendar,
)
