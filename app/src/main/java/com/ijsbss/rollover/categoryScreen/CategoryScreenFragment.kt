package com.ijsbss.rollover.categoryScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ijsbss.rollover.recyclerViews.ExpenseRecyclerViewAdapter
import com.ijsbss.rollover.R
import com.ijsbss.rollover.data.db.AppDatabase
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.databinding.FragmentCategoryScreenBinding
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CategoryScreenFragment : Fragment() {
    private lateinit var categoryScreenFragmentViewModel: CategoryScreenFragmentViewModel
    private var _binding: FragmentCategoryScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_screen, container, false)
        val dao = AppDatabase.getInstance(requireActivity().application).categoryDao()
        val repository = CategoryRepository(dao)
        val factory = CategoryScreenViewModelFactory(repository)
        categoryScreenFragmentViewModel = ViewModelProvider(this, factory).get(CategoryScreenFragmentViewModel::class.java)
        binding.myViewModel = categoryScreenFragmentViewModel
        binding.lifecycleOwner = this

        val decimalFormat = DecimalFormat("0.00")

        val categoryName = arguments?.getString("categoryName")
        val expectation = arguments?.getFloat("expectation")
        val totalSpent = arguments?.getFloat("totalSpent")!!
        val categoryColor = arguments?.getInt("categoryColor")
        val rolloverPeriod = arguments?.getByte("rolloverPeriod")!!
        val date = arguments?.getString("date")

        val expectationWithTwoDecimalFormat = decimalFormat.format(expectation)
        val dollarSignExpectation = "$$expectationWithTwoDecimalFormat"

        val numberOfDays = ((ChronoUnit.DAYS.between(LocalDate.parse(date, DateTimeFormatter.ISO_DATE), LocalDate.now() )).rem(rolloverPeriod))

        val totalSpentWithTwoDecimalFormat = decimalFormat.format(totalSpent)
        val dollarSignTotalSpent = "$$totalSpentWithTwoDecimalFormat"

        val baseAvailable = expectation?.div(rolloverPeriod)
        val available = baseAvailable?.plus(baseAvailable.times(numberOfDays))?.minus(totalSpent)
        val availableWithTwoDecimalFormat = decimalFormat.format(available)
        val dollarSignAvailable = "$$availableWithTwoDecimalFormat"

        val rolledOverWithTwoDecimalFormat = if(numberOfDays.toInt() == 0) {
            decimalFormat.format(0.00F)
        } else{
            decimalFormat.format(baseAvailable?.times(numberOfDays)?.minus(totalSpent))
        }

        val dollarSignRolledOver = "$$rolledOverWithTwoDecimalFormat"

        binding.categoryNameView.text = categoryName
        binding.categoryBack.setBackgroundColor(categoryColor!!)
        binding.categoryNameView.setBackgroundColor(categoryColor)
        binding.catAvailableView.text = dollarSignAvailable
        binding.catSpentView.text = dollarSignTotalSpent
        binding.rolledOverView.text = dollarSignRolledOver
        binding.catExpectationView.text = dollarSignExpectation

        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.category_back).setOnClickListener {
            findNavController().navigate(R.id.action_CategoryScreenFragment_to_MainFragment)
        }

        view.findViewById<Button>(R.id.add_expense_button).setOnClickListener {
            val categoryId = arguments?.getInt("categoryId")!!
            val categoryName = arguments?.getString("categoryName")
            val categoryColor = arguments?.getInt("categoryColor")
            val totalSpent = arguments?.getFloat("totalSpent")
            val expectation = arguments?.getFloat("expectation")
            val rolloverPeriod = arguments?.getByte("rolloverPeriod")
            val date = arguments?.getString("date")

            val bundle = bundleOf(
                    ("categoryId" to categoryId),
                    ("categoryName" to categoryName),
                    ("categoryColor" to categoryColor),
                    ("totalSpent" to totalSpent),
                    ("expectation" to expectation),
                    ("rolloverPeriod" to rolloverPeriod),
                    ("date" to date)
            )

            findNavController().navigate(R.id.action_CategoryScreenFragment_to_AddExpenseScreenFragment, bundle)
        }
    }

    private fun initRecyclerView() {
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(this.context)
        displayExpensesList()
    }

    private fun displayExpensesList() {
        val categoryId = arguments?.getInt("categoryId")!!
        categoryScreenFragmentViewModel.expenses(categoryId).observe(binding.lifecycleOwner!!, {
            Log.i("MYTAG", it.toString())
            binding.expenseRecyclerView.adapter = ExpenseRecyclerViewAdapter(it)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}