package com.ijsbss.rollover

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ijsbss.rollover.data.db.AppDatabase
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment() : Fragment()  {

    private lateinit var firstFragmentViewModel: FirstFragmentViewModel //by viewModels()
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val dao = AppDatabase.getInstance(requireActivity().application).categoryDao
        val repository = CategoryRepository(dao)
        val factory = FirstFragmentViewModelFactory(repository)
        firstFragmentViewModel = ViewModelProvider(this, factory).get(FirstFragmentViewModel::class.java)
        binding.myViewModel = firstFragmentViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
            view.findViewById<Button>(R.id.add_category_button).setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
    }

    private fun initRecyclerView(){
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this.context)
        displayCategoriesList()
    }

    private fun displayCategoriesList(){
        firstFragmentViewModel.categories.observe(binding.lifecycleOwner!!, Observer {
            Log.i("MYTAG", it.toString())
            binding.categoryRecyclerView.adapter = MyRecyclerViewAdapter(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
