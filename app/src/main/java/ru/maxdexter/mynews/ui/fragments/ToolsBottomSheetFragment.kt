package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.FragmentToolsBottomSheetBinding
import ru.maxdexter.mynews.settings.AppPreferences
import ru.maxdexter.mynews.ui.viewmodels.toolsViewModel.ToolsViewModel
import ru.maxdexter.mynews.ui.viewmodels.toolsViewModel.ToolsViewModelFactory

class ToolsBottomSheetFragment: BottomSheetDialogFragment() {
    lateinit var binding: FragmentToolsBottomSheetBinding
    lateinit var viewModel: ToolsViewModel
    lateinit var viewModelFactory: ToolsViewModelFactory
    var isDarkTheme = false

    override fun onCreate(savedInstanceState: Bundle?) {
        isDarkTheme = AppPreferences(requireContext()).isDarkTheme
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tools_bottom_sheet, container, false)

        val preferences = AppPreferences(requireActivity())
        viewModelFactory = ToolsViewModelFactory(preferences)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ToolsViewModel::class.java)
        binding.switchTheme.isChecked = isDarkTheme
        binding.switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->

            viewModel.setTheme(isChecked)
            requireActivity().recreate()
        }

        return binding.root
    }
}