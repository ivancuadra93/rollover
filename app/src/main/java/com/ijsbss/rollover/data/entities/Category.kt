package com.ijsbss.rollover.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
        @PrimaryKey @ColumnInfo(name = "id") var categoryId: Int,

        var name: String,
        var expectation: Float,
        @ColumnInfo(name = "total_spent") var totalSpent: Float,

        // In number of days
        @ColumnInfo(name = "rollover_period") var rolloverPeriod: Byte,
        var color: String,
        var threshold: Float,
        @ColumnInfo(name = "expenses_id") var expensesId: Int,
        @ColumnInfo(name = "view_order") var viewOrder: Byte,
)
