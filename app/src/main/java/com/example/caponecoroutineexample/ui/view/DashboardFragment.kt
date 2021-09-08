package com.example.caponecoroutineexample.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import com.example.caponecoroutineexample.R
import com.example.caponecoroutineexample.databinding.DashboardFragmentBinding
import com.example.caponecoroutineexample.ui.viewmodel.MainViewModel
import com.example.caponecoroutineexample.utility.LOG_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var _binding: DashboardFragmentBinding? = null
    private val binding: DashboardFragmentBinding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.etColor.isEnabled = true
            binding.tilColor.isEnabled = true
            binding.etSize.isEnabled = true
            binding.tilSize.isEnabled = true
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DashboardFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.let{ binding ->
            binding.swGIF.setOnCheckedChangeListener { _ , isChecked ->
                if (isChecked){
                    binding.imageMenuTv.isEnabled = false
                    viewModel.tag = "gif"
                } else {
                    binding.imageMenuTv.isEnabled = true
                    viewModel.tag = ""
                }
            }

            binding.imageAutoComplete.setOnItemClickListener { parent, view, position, id ->
                viewModel.tag = parent.getItemAtPosition(position).toString()
            }

            binding.filterAutoComplete.setOnItemClickListener { parent, view, position, id ->
                viewModel.imageFilter = parent.getItemAtPosition(position).toString()
            }

            binding.etText.addTextChangedListener(textWatcher)

            binding.btnSubmit.setOnClickListener {
                viewModel.imageText = binding.etText.text.toString()
                viewModel.textColor = binding.etColor.text.toString()
                viewModel.textSize = binding.etSize.text.toString()
                submit()
                view.findNavController().navigate(R.id.action_dashboardFragment_to_catFragment)
            }

            binding.btnClear.setOnClickListener {
                binding.swGIF.isChecked = false
                binding.imageMenuTv.isEnabled = true
                viewModel.tag = ""
                binding.imageAutoComplete.dismissDropDown()
                binding.imageAutoComplete.text.clear()
                setTags()
                Log.i(LOG_TAG, "Tag Dropdown reset called from Clear button")
                viewModel.imageFilter = ""
                binding.filterAutoComplete.dismissDropDown()
                binding.filterAutoComplete.text.clear()
                setFilter()
                Log.i(LOG_TAG, "Filter Dropdown reset called from clear button")
                binding.etText.text?.clear()
                binding.etSize.text?.clear()
                binding.etColor.text?.clear()
            }
        }
    }



    override fun onResume() {
        super.onResume()
        setTags()
        Log.i(LOG_TAG, "Tag Dropdown initial setup")
        setFilter()
        Log.i(LOG_TAG, "Filter Dropdown inital setup")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun submit() {
        viewModel.getCats()
    }

    private fun setTags() {
        val imageTypeMenuItem = resources.getStringArray(R.array.image_menu)
        Log.i(LOG_TAG, "Set imageTypeMenuItem")
        val imageAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, imageTypeMenuItem)
        Log.i(LOG_TAG,"Set imageAdapter")
        binding.imageAutoComplete.setAdapter(imageAdapter)
        Log.i(LOG_TAG,"Tag Dropdown should be ready")
    }

    private fun setFilter() {
        val filterMenuItem = resources.getStringArray(R.array.filter_menu)
        Log.i(LOG_TAG, "Set filterMenuItem")
        val filterAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, filterMenuItem)
        Log.i(LOG_TAG, "Set filterAdapter")
        binding.filterAutoComplete.setAdapter(filterAdapter)
        Log.i(LOG_TAG, "Filter Dropdown should be ready")
    }

}