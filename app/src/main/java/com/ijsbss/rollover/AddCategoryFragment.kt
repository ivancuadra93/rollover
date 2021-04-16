package com.ijsbss.rollover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ijsbss.rollover.data.db.AppDatabase
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.databinding.FragmentAddCategoryBinding
//import kotlinx.android.synthetic.main.fragment_add_category.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddCategoryFragment : Fragment() {
    private lateinit var addCategoryFragmentViewModel: AddCategoryFragmentViewModel
    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_category, container, false)
        val dao = AppDatabase.getInstance(requireActivity().application).categoryDao()
        val repository = CategoryRepository(dao)
        addCategoryFragmentViewModel = AddCategoryFragmentViewModel(repository)
        binding.viewModel = addCategoryFragmentViewModel
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//            if((addCategoryFragmentViewModel.inputName.value == null || addCategoryFragmentViewModel.inputExpectation.value == null || addCategoryFragmentViewModel.inputRolloverPeriod.value == null || addCategoryFragmentViewModel.inputThreshold.value == null )){
//                view.findViewById<Button>(R.id.save_button).isEnabled = false
//            }

            view.findViewById<Button>(R.id.save_button).setOnClickListener {
                if((addCategoryFragmentViewModel.inputName.value != null && addCategoryFragmentViewModel.inputExpectation.value != null && addCategoryFragmentViewModel.inputRolloverPeriod.value != null && addCategoryFragmentViewModel.inputThreshold.value != null )) {
                    addCategoryFragmentViewModel.saveOrUpdate()
                    findNavController().navigate(R.id.action_AddCategoryFragment_to_MainFragment)
                }
                else{

                }
            }
            view.findViewById<Button>(R.id.cancel_button).setOnClickListener {
                findNavController().navigate(R.id.action_AddCategoryFragment_to_MainFragment)
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
