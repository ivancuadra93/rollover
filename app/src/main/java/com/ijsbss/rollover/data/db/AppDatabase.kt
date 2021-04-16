package com.ijsbss.rollover.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ijsbss.rollover.data.entities.Category

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao() : CategoryDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null
            fun getInstance(context: Context) : AppDatabase{
                synchronized(this){
                    var instance : AppDatabase? = INSTANCE
                    if(instance==null){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "categories"
                        ).build()
                    }
                    return instance
                }
            }
    }

}