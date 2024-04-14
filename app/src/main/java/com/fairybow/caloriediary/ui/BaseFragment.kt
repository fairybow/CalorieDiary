package com.fairybow.caloriediary.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val viewModelClass: Class<VM>
) : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!! // Only valid between onCreateView & onDestroyView
    private lateinit var navController: NavController
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

        lifecycleScope.launch {
            whileOnCreateView()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        lifecycleScope.launch {
            whileOnViewCreated()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun addNavFab(fab: FloatingActionButton, rId: Int? = null) {
        val layoutParams = fab.layoutParams as CoordinatorLayout.LayoutParams
        val margin = 80

        layoutParams.gravity = Gravity.BOTTOM or Gravity.END
        layoutParams.setMargins(0, 0, margin, (margin + 150))
        // prev: ... (margin + (bottomNavHeight()?: margin))
        // ^ Original code (using nav height) causes issues,
        // where navView (MainActivity) is not initialized yet
        // when we use this method in DayFragment (our start
        // destination)

        fab.layoutParams = layoutParams

        if (rId != null) {
            fab.setOnClickListener {
                navController.navigate(rId)
            }
        }
    }

    /*private fun bottomNavHeight(): Int? {
        return when {
            activity is MainActivity -> (activity as MainActivity).bottomNavHeight()
            else -> null
        }
    }*/

    open suspend fun whileOnCreateView() { /* Optional */ }
    open suspend fun whileOnViewCreated() { /* Optional */ }
}
