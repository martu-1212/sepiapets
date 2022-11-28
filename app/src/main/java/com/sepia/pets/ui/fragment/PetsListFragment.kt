package com.sepia.pets.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sepia.pets.R
import com.sepia.pets.`interface`.OnItemClickListener
import com.sepia.pets.base.BaseFragment
import com.sepia.pets.databinding.FragmentPetsListBinding
import com.sepia.pets.model.Pets
import com.sepia.pets.ui.adapter.PetsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetsListFragment : BaseFragment<FragmentPetsListBinding>(),OnItemClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_pets_list

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPetsListBinding
        get() = FragmentPetsListBinding::inflate

//    private val viewModel: MyFeedViewModel by viewModels()

    var mPetsList: ArrayList<Pets>? = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observerListViewModel()

    }

    private fun initView() {

    }


    private fun setAdapter() {
        binding.rvPets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPets.adapter = mPetsList?.let { it1 ->
            PetsListAdapter(
                requireContext(),
                it1,this)
        }

    }


    private fun observerListViewModel() {
        lifecycleScope.launchWhenStarted {

        }
    }

    override fun onItemViewClick(view: View, position: Int, id: String) {

    }

}