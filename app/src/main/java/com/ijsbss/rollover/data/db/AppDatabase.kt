package com.ijsbss.rollover.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ijsbss.rollover.data.entities.Category

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao
}