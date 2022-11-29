package com.sepia.pets.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sepia.pets.R
import com.sepia.pets.base.BaseFragment
import com.sepia.pets.databinding.DialogTimeoutBinding
import com.sepia.pets.databinding.FragmentPetsListBinding
import com.sepia.pets.interfac.OnPetsItemClickListener
import com.sepia.pets.model.Pet
import com.sepia.pets.ui.adapter.PetsListAdapter
import com.sepia.pets.ui.viewmodel.PetsViewModel
import com.sepia.pets.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PetsListFragment : BaseFragment<FragmentPetsListBinding>(), OnPetsItemClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_pets_list

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPetsListBinding
        get() = FragmentPetsListBinding::inflate

    private val viewModel: PetsViewModel by viewModels()

    private var mPetsList: ArrayList<Pet>? = ArrayList()

    private var workHours: String? = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observerListViewModel()
    }

    private fun initView() {
        binding.toolbar.tvTitle.text = getString(R.string.pets_list)
        viewModel.getApptime(requireContext())
    }


    private fun setAdapter() {
        binding.rvPets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPets.adapter = mPetsList?.let { it1 ->
            PetsListAdapter(
                requireContext(),
                it1, this
            )
        }

    }


    private fun observerListViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.petsListObservable.observe(requireActivity()) {
                when (it) {
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        mPetsList = ArrayList()
                        mPetsList?.addAll(it.data.pets!!)
                        setAdapter()
                    }
                    is DataState.Error -> {
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.appTimeObservable.observe(requireActivity()) {
                when (it) {
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        workHours = it.data.settings!!.workHours.toString()
                        checkForTime()
                    }
                    is DataState.Error -> {
                        showTimeOutDialog()
                    }
                }
            }
        }
    }

    private fun checkForTime() {
        val str = workHours!!.split(" ").toTypedArray()
        val dateFormat = SimpleDateFormat("HH:mm")
        val dateFormat2 = SimpleDateFormat("hh:mm aa")
        val dateStart : Date? = dateFormat.parse(str.get(1))
        val startDate: Date? = dateFormat2.parse(dateFormat2.format(dateStart))
        val dateEnd : Date? = dateFormat.parse(str.get(3))
        val endDate: Date? = dateFormat2.parse(dateFormat2.format(dateEnd))

        if (isNowBetweenDateTime(
                startDate,
                endDate
            )
        ) viewModel.getPetsList(requireContext()) else showTimeOutDialog()
    }

    private fun isNowBetweenDateTime(s: Date?, e: Date?): Boolean {
        val dateFormat2 = SimpleDateFormat("hh:mm aa")
        val date: Date? = dateFormat2.parse(dateFormat2.format(Date()))
        return date!!.after(s) && date.before(e)
    }

    override fun onItemViewClick(view: View, position: Int) {
        val bundle = Bundle()
        bundle.putString("title", mPetsList!!.get(position).title!!)
        bundle.putString("content", mPetsList!!.get(position).contentUrl!!)
        navController.navigate(R.id.action_petList_to_petDetails, bundle)
    }

    private fun showTimeOutDialog() {
        val alertDialog =
            AlertDialog.Builder(requireContext(), R.style.TransparentProgressDialog).create()

        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_timeout, null, false)
        val binding = DialogTimeoutBinding.bind(view)

        alertDialog.setView(binding.root)
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)

        binding.tvOk.setOnClickListener {
            alertDialog.dismiss()
            requireActivity().finish()
        }

        alertDialog.show()
    }

}