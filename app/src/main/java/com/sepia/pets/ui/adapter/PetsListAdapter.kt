package com.sepia.pets.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sepia.pets.R
import com.sepia.pets.interfac.OnPetsItemClickListener
import com.sepia.pets.databinding.ItemPetsBinding
import com.sepia.pets.model.Pet
class PetsListAdapter(
    private val mContext: Context,
    private val mPetsList: ArrayList<Pet>,
    private val mClickListener: OnPetsItemClickListener,
    ) : RecyclerView.Adapter<PetsListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPetsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPetsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mPetsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val options: RequestOptions = RequestOptions()
            .fitCenter()
            .placeholder(R.drawable.pet_placeimage)
            .error(R.drawable.pet_placeimage)

        Glide.with(mContext)
            .load(mPetsList.get(position).imageUrl)
            .apply(options)
            .into(holder.binding.ivPet)

        holder.binding.tvTitle.text = mPetsList[position].title

        holder.itemView.setOnClickListener {
            mClickListener.onItemViewClick(holder.itemView,position)
        }
    }

}