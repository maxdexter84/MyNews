package ru.maxdexter.mynews.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.BottomNavDrawerFragmentBinding

class BottomNavDrawerMenu: BottomSheetDialogFragment() {

    private lateinit var binding:BottomNavDrawerFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.bottom_nav_drawer_fragment, container, false)

        return binding.root
    }

}