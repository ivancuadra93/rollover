package com.ijsbss.rollover.data

import android.graphics.Color
import android.media.VolumeShaper
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
    private val categoryA = Category(0, "A", 120.00f, 32.54f, 14, Color.RED, 20.00f, 10, 1)
    private val categoryB = Category(0, "B", 140.00f, 74.21f, 14, Color.BLUE, 20.00f, 11,2)
    private val categoryC = Category(0, "C", 190.00f, 85.60f, 14, Color.GREEN, 20.00f, 12, 3)

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
    }

    @Test
    fun testDeleteCategory() = runBlocking {
        var categories = categoryDao.getCategories().getOrAwaitValue()
        categoryDao.delete(categories[1].id)

        categories = categoryDao.getCategories().getOrAwaitValue()
        assertThat(categories.size, equalTo(2))
    }

    @Test
    fun testAddCategory() = runBlocking {
        categoryDao.insert(Category(0,"D",210.00f,50.00f,14,Color.GREEN,20.00f,13,4))

        val categories = categoryDao.getCategories().getOrAwaitValue()
        assertThat(categories.size, equalTo(4))
    }

    @Test
    fun testDeleteAllCategories() = runBlocking {
        categoryDao.deleteAll()

        val categories = categoryDao.getCategories().getOrAwaitValue()
        assertThat(categories.size, equalTo(0))
    }

    @Test
    fun testCategoryID() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assert(categories[0].id != categoryA.id)
        assert(categories[1].id != categoryB.id)
        assert(categories[2].id != categoryC.id)
    }

    @Test
    fun testCategoryName() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assertThat(categories[0].name, equalTo(categoryA.name))
        assertThat(categories[1].name, equalTo(categoryB.name))
        assertThat(categories[2].name, equalTo(categoryC.name))
    }

    @Test
    fun testCategoryExpectation() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assertThat(categories[0].expectation, equalTo(categoryA.expectation))
        assertThat(categories[1].expectation, equalTo(categoryB.expectation))
        assertThat(categories[2].expectation, equalTo(categoryC.expectation))
    }

    @Test
    fun testCategoryTotalSpent() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assertThat(categories[0].totalSpent, equalTo(categoryA.totalSpent))
        assertThat(categories[1].totalSpent, equalTo(categoryB.totalSpent))
        assertThat(categories[2].totalSpent, equalTo(categoryC.totalSpent))
    }

    @Test
    fun testCategoryRolloverPeriod() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assertThat(categories[0].rolloverPeriod, equalTo(categoryA.rolloverPeriod))
        assertThat(categories[1].rolloverPeriod, equalTo(categoryB.rolloverPeriod))
        assertThat(categories[2].rolloverPeriod, equalTo(categoryC.rolloverPeriod))
    }

    @Test
    fun testCategoryColor() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assertThat(categories[0].color, equalTo(categoryA.color))
        assertThat(categories[1].color, equalTo(categoryB.color))
        assertThat(categories[2].color, equalTo(categoryC.color))
    }

    @Test
    fun testCategoryThreshold() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assertThat(categories[0].threshold, equalTo(categoryA.threshold))
        assertThat(categories[1].threshold, equalTo(categoryB.threshold))
        assertThat(categories[2].threshold, equalTo(categoryC.threshold))
    }

    @Test
    fun testCategoryExpensesId() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assertThat(categories[0].expensesId, equalTo(categoryA.expensesId))
        assertThat(categories[1].expensesId, equalTo(categoryB.expensesId))
        assertThat(categories[2].expensesId, equalTo(categoryC.expensesId))
    }

    @Test
    fun testCategoryViewOrder() = runBlocking {
        val categories = categoryDao.getCategories().getOrAwaitValue()

        assertThat(categories[0].viewOrder, equalTo(categoryA.viewOrder))
        assertThat(categories[1].viewOrder, equalTo(categoryB.viewOrder))
        assertThat(categories[2].viewOrder, equalTo(categoryC.viewOrder))
    }
}