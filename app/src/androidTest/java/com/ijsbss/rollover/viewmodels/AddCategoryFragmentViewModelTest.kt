package com.ijsbss.rollover.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ijsbss.rollover.AddCategoryFragmentViewModel
import com.ijsbss.rollover.MainCoroutineRule
import com.ijsbss.rollover.data.db.AppDatabase
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.runBlockingTest
import com.ijsbss.rollover.utilities.*
import org.hamcrest.Matchers.equalTo
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddCategoryFragmentViewModelTest {
    private lateinit var db: AppDatabase
    private lateinit var viewModel: AddCategoryFragmentViewModel
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
        viewModel = AddCategoryFragmentViewModel(repository)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testCategoryInsertWithCorrectValues() = coroutineRule.runBlockingTest {
        // check db for empty db
        assertThat(repository.categories.getOrAwaitValue().size, equalTo(0))

        // add values to bindable data to simulate input
        viewModel.inputName.value = "food"
        viewModel.inputExpectation.value = "200.00"
        viewModel.inputRolloverPeriod.value = "14"
        viewModel.inputColor.value = "Red"
        viewModel.inputThreshold.value = "90"

        // check values were added to viewModel
        assertThat(viewModel.inputName.value, equalTo("food"))
        assertThat(viewModel.inputExpectation.value, equalTo("200.00"))
        assertThat(viewModel.inputRolloverPeriod.value, equalTo("14"))
        assertThat(viewModel.inputColor.value, equalTo("Red"))
        assertThat(viewModel.inputThreshold.value, equalTo("90"))

        // save as input field values as a category in db
        viewModel.saveOrUpdate()

        // check for new category added to db
        val vmCategories = repository.categories.getOrAwaitValue()
        assertThat(vmCategories.size, equalTo(1))
        assertThat(vmCategories[0].name, equalTo("FOOD"))
        assertThat(vmCategories[0].expectation, equalTo(200.00f))
        assertThat(vmCategories[0].rolloverPeriod, equalTo(14))
        assertThat(vmCategories[0].color, equalTo(RED))
        assertThat(vmCategories[0].threshold, equalTo(90.0F))
    }

    // test threshold over 100% - should fail

    // test rollover period limits - not more than a month?

    // test delete for category

    // test name capitalization for storage
}