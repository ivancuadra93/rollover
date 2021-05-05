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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ijsbss.rollover.recyclerViews.ExpenseRecyclerViewAdapter
import com.ijsbss.rollover.R
import com.ijsbss.rollover.data.db.AppDatabase
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.databinding.FragmentCategoryScreenBinding
import java.text.DecimalFormat

class CategoryScreenFragment() : Fragment() {
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

        val availableWithTwoDecimalFormat = decimalFormat.format(arguments?.getFloat("expectation")?.minus(arguments?.getFloat("totalSpent")!!))
        val dollarSignAvailable = "$$availableWithTwoDecimalFormat"


        val totalSpentWithTwoDecimalFormat = decimalFormat.format(arguments?.getFloat("totalSpent"))
        val dollarSignTotalSpent = "$$totalSpentWithTwoDecimalFormat"

        val expectationWithTwoDecimalFormat = decimalFormat.format(arguments?.getFloat("expectation"))
        val dollarSignExpectation = "$$expectationWithTwoDecimalFormat"

        val categoryName = arguments?.getString("categoryName")
        val categoryColor = arguments?.getInt("categoryColor")


        binding.catAvailableView.text = dollarSignAvailable
        binding.categoryNameView.text = categoryName
        binding.categoryNameView.setBackgroundColor(categoryColor!!)
        binding.catSpentView.text = dollarSignTotalSpent
        binding.catExpectationView.text = dollarSignExpectation
        binding.categoryBack.setBackgroundColor(categoryColor)

        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.category_back).setOnClickListener {
            findNavController().navigate(R.id.action_CategoryScreenFragment_to_MainFragment)
            //findNavController().popBackStack()
        }

        view.findViewById<Button>(R.id.add_expense_button).setOnClickListener {
            val categoryId = arguments?.getInt("categoryId")!!
            val categoryName = arguments?.getString("categoryName")
            val categoryColor = arguments?.getInt("categoryColor")
            val totalSpent = arguments?.getFloat("totalSpent")
            val expectation = arguments?.getFloat("expectation")
            val bundle = bundleOf(
                    ("categoryId" to categoryId),
                    ("categoryName" to categoryName),
                    ("categoryColor" to categoryColor),
                    ("totalSpent" to totalSpent),
                    ("expectation" to expectation)
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
            binding.expenseRecyclerView.adapter = ExpenseRecyclerViewAdapter(it, categoryId)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}