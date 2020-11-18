package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.settings.AppPreferences
import ru.maxdexter.mynews.ui.viewmodels.toolsViewModel.ToolsViewModel
import ru.maxdexter.mynews.ui.viewmodels.toolsViewModel.ToolsViewModelFactory

class ToolsBottomSheetFragment: BottomSheetDialogFragment() {

    lateinit var viewModel: ToolsViewModel
    lateinit var viewModelFactory: ToolsViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val appPreferences = AppPreferences(requireContext())
        viewModelFactory = ToolsViewModelFactory(appPreferences)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ToolsViewModel::class.java)



        return inflater.inflate(R.layout.fragment_tools_bottom_sheet, container, false)
    }
}