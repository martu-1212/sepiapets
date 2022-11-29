package com.sepia.pets.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.sepia.pets.ui.activity.MainActivity


abstract class BaseFragment<B : ViewBinding> : Fragment() {
    @get:LayoutRes
    abstract val layoutId: Int
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B
    lateinit var navController: NavController

    lateinit var binding: B

    fun mainActivity() = requireActivity() as MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

    }
    override fun onDestroyView() {
        onBackPressedCallback.remove()
        super.onDestroyView()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    open fun onBackPressed() {
        navController.navigateUp()
    }

}