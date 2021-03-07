package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.FragmentToolsBottomSheetBinding
import ru.maxdexter.mynews.settings.AppPreferences
import ru.maxdexter.mynews.ui.viewmodels.toolsViewModel.ToolsViewModel
import ru.maxdexter.mynews.ui.viewmodels.toolsViewModel.ToolsViewModelFactory

class SettingsFragment: Fragment(){
   private lateinit var binding: FragmentToolsBottomSheetBinding
   private lateinit var viewModel: ToolsViewModel
   private lateinit var viewModelFactory: ToolsViewModelFactory
   private var isDarkTheme = false

    override fun onCreate(savedInstanceState: Bundle?) {
        isDarkTheme = AppPreferences(requireContext()).isDarkTheme
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tools_bottom_sheet, container, false)

        val preferences = AppPreferences(requireActivity())
        viewModelFactory = ToolsViewModelFactory(preferences)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ToolsViewModel::class.java)
        setTheme()

        return binding.root
    }

    private fun setTheme() {
        binding.switchTheme.isChecked = isDarkTheme
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked)
            requireActivity().recreate()
        }
    }
}