package com.ijsbss.rollover.data

import android.graphics.Color
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import com.ijsbss.rollover.data.db.AppDatabase
import com.ijsbss.rollover.data.db.CategoryDao
import com.ijsbss.rollover.data.entities.Category
import com.ijsbss.rollover.utilities.getOrAwaitValue
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var categoryDao: CategoryDao
    private val categoryA = Category(1234, "A", 120.00f, 32.54f, 14, Color.RED, 20.00f, 10, 1)
    private val categoryB = Category(1285, "B", 140.00f, 74.21f, 14, Color.BLUE, 20.00f, 11,2)
    private val categoryC = Category(1676, "C", 190.00f, 85.60f, 14, Color.GREEN, 20.00f, 12, 3)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        categoryDao = db.categoryDao()

        // Insert categories out of order
        categoryDao.insert(categoryB)
        categoryDao.insert(categoryC)
        categoryDao.insert(categoryA)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testGetCategories() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assertThat(categories.size, equalTo(3))

        // Ensure category list is sorted by view order
        assertThat(categories[0], equalTo(categoryA))
        assertThat(categories[1], equalTo(categoryB))
        assertThat(categories[2], equalTo(categoryC))
    }

    @Test
    fun testDeleteCategory() = runBlocking {
        categoryDao.delete(categoryB)

        val categories = categoryDao.getCategories().getOrAwaitValue()
        assertThat(categories.size, equalTo(2))
    }
}