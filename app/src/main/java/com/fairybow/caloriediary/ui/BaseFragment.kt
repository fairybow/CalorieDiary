package com.fairybow.caloriediary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.fairybow.caloriediary.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val viewModelClass: Class<VM>
) : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!! // Only valid between onCreateView & onDestroyView
    protected lateinit var navController: NavController
    protected lateinit var sharedViewModel: SharedViewModel
    protected lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        viewModel = ViewModelProvider(this)[viewModelClass]
        sharedViewModel = activity?.run {
            ViewModelProvider(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        val root: View = binding.root

        lifecycleScope.launch {
            whileOnCreateView()
        }

        return root
    }

    open suspend fun whileOnCreateView() { /* Optional */ }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        lifecycleScope.launch {
            whileOnViewCreated()
        }
    }

    open suspend fun whileOnViewCreated() { /* Optional */ }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun bottomNavView(): BottomNavigationView? {
        return if (activity is MainActivity) {
            (activity as MainActivity).getBottomNav()
        } else {
            null
        }
    }
}
