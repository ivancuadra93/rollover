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
import org.hamcrest.Matchers.lessThanOrEqualTo
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

        // save input field values as a category in db
        viewModel.saveOrUpdate()

        // check for new category added to db
        val vmCategories = repository.categories.getOrAwaitValue()
        assertThat(vmCategories.size, equalTo(1))
        assertThat(vmCategories[0].name, equalTo("FOOD"))
        assertThat(vmCategories[0].expectation, equalTo(200.00f))
        assertThat(vmCategories[0].rolloverPeriod, equalTo(14))
        assertThat(vmCategories[0].color, equalTo(RED))
        assertThat(vmCategories[0].threshold, equalTo(90.0F))

        // remove from db
        repository.delete(vmCategories[0].id)
    }

    @Test
    fun testNameCapitalization() = coroutineRule.runBlockingTest {
        // add values to bindable data to simulate input
        viewModel.inputName.value = "food"
        viewModel.inputExpectation.value = "200.00"
        viewModel.inputRolloverPeriod.value = "45"
        viewModel.inputColor.value = "Red"
        viewModel.inputThreshold.value = "90"

        // save input field values as a category in db
        viewModel.saveOrUpdate()

        // check Name was Capitalized for storage
        val vmCategories = repository.categories.getOrAwaitValue()
        assert(vmCategories[0].name != "food")

        // remove from db
        repository.delete(vmCategories[0].id)
    }

    @Test
    fun testDuplicateCategoryNames() = coroutineRule.runBlockingTest {
        @Suppress("UNUSED_PARAMETER")
        for (i in 1..2) {
            // add values to bindable data to simulate input
            viewModel.inputName.value = "food"
            viewModel.inputExpectation.value = "200.00"
            viewModel.inputRolloverPeriod.value = "45"
            viewModel.inputColor.value = "Red"
            viewModel.inputThreshold.value = "90"

            // save input field values as a category in db
            viewModel.saveOrUpdate()
        }

        // check Name was Capitalized for storage
        val vmCategories = repository.categories.getOrAwaitValue()
        assertThat(vmCategories.size, equalTo(1))

        // remove from db
        repository.delete(vmCategories[0].id)
    }

    @Test
    fun testThresholdLimitOver100Percent() = coroutineRule.runBlockingTest {
        // add values to bindable data to simulate input
        viewModel.inputName.value = "food"
        viewModel.inputExpectation.value = "200.00"
        viewModel.inputRolloverPeriod.value = "14"
        viewModel.inputColor.value = "Red"
        viewModel.inputThreshold.value = "150.00"

        // save input field values as a category in db
        viewModel.saveOrUpdate()

        // check that view model rejected input of greater than 100.00 for threshold
        val vmCategories = repository.categories.getOrAwaitValue()
        assertThat(vmCategories[0].threshold, lessThanOrEqualTo(100.00F))

        // remove from db
        repository.delete(vmCategories[0].id)
    }

    fun testRolloverPeriodLimitOver28Days() = coroutineRule.runBlockingTest {
        // add values to bindable data to simulate input
        viewModel.inputName.value = "food"
        viewModel.inputExpectation.value = "200.00"
        viewModel.inputRolloverPeriod.value = "45"
        viewModel.inputColor.value = "Red"
        viewModel.inputThreshold.value = "90"


        // save input field values as a category in db
        viewModel.saveOrUpdate()

        // check that view model rejected input of greater than 28 for rollover period
        val vmCategories = repository.categories.getOrAwaitValue()
        assertThat(vmCategories[0].rolloverPeriod, lessThanOrEqualTo(28))

        // remove from db
        repository.delete(vmCategories[0].id)
    }
}