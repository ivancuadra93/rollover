package com.ijsbss.rollover.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ijsbss.rollover.data.entities.Category
import com.ijsbss.rollover.data.entities.Expense

@Database(entities = [Category::class, Expense::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao
    abstract fun expenseDao() : ExpenseDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase{
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "rollover-db"
                    ).build()
                }

                return instance
            }
        }
    }

}