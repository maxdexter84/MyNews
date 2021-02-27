package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.BottomNavDrawerFragmentBinding

class BottomNavDrawerMenu: BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private lateinit var binding:BottomNavDrawerFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.bottom_nav_drawer_fragment, container, false)

        binding.designNavigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.general ->{
                    findNavController().navigate(BottomNavDrawerMenuDirections.actionBottomNavDrawerMenuToBreakingNewsFragment("general"))
                     true
                }
                R.id.business ->{
                    findNavController().navigate(BottomNavDrawerMenuDirections.actionBottomNavDrawerMenuToBreakingNewsFragment("business"))
                     true
                }
                R.id.sports -> {
                    findNavController().navigate(BottomNavDrawerMenuDirections.actionBottomNavDrawerMenuToBreakingNewsFragment("sports"))
                     true
                }
                R.id.health -> {
                    findNavController().navigate(BottomNavDrawerMenuDirections.actionBottomNavDrawerMenuToBreakingNewsFragment("health"))
                     true
                }
                R.id.science -> {
                    findNavController().navigate(BottomNavDrawerMenuDirections.actionBottomNavDrawerMenuToBreakingNewsFragment("science"))
                     true
                }
                R.id.technology ->  {
                    findNavController().navigate(BottomNavDrawerMenuDirections.actionBottomNavDrawerMenuToBreakingNewsFragment("technology"))
                     true
                }
                R.id.entertainment -> {
                    findNavController().navigate(BottomNavDrawerMenuDirections.actionBottomNavDrawerMenuToBreakingNewsFragment("entertainment"))
                   true
                }
                else -> false
            }
        }
        return binding.root
    }

}