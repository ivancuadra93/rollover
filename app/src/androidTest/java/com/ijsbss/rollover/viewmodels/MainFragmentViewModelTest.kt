package com.ijsbss.rollover.viewmodels

import android.graphics.Color
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ijsbss.rollover.MainCoroutineRule
import com.ijsbss.rollover.MainFragmentViewModel
import com.ijsbss.rollover.data.db.AppDatabase
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.data.entities.Category
import com.ijsbss.rollover.runBlockingTest
import com.ijsbss.rollover.utilities.*
import org.hamcrest.Matchers.equalTo
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentViewModelTest {
    private lateinit var db: AppDatabase
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var repository: CategoryRepository
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    var rule = RuleChain
        .outerRule(instantTaskExecutorRule)
        .around(coroutineRule)

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repository = CategoryRepository(db.categoryDao())
        viewModel = MainFragmentViewModel(repository)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testCategoryDeletePass() = coroutineRule.runBlockingTest {
        val foodCategory = Category(0, "FOOD", 140.00f, 74.21f, 14, Color.BLUE, 20.00f, 11,2)
        repository.insert(foodCategory)

        // check db has a category to delete
        assertThat(repository.categories.getOrAwaitValue().size, equalTo(1))

        // delete category from view model
        viewModel.deleteCategory(foodCategory.id)

        assertThat(viewModel.categories.getOrAwaitValue().size, equalTo(0))
    }

}