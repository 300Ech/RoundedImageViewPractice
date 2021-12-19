package com.evertschavez.dogs

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.evertschavez.dogs.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class DogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemDogBinding.bind(view)

    fun bind(image: String, position: Int) {
        Picasso.get().load(image).into(binding.ivDog)

        if (position % 2 == 0)
            binding.ivDog.setRoundedCorners(RoundedImageView.CornerType.CORNER_TOP_LEFT.value + RoundedImageView.CornerType.CORNER_BOTTOM_LEFT.value)
        else
            binding.ivDog.setRoundedCorners(RoundedImageView.CornerType.CORNER_TOP_RIGHT.value + RoundedImageView.CornerType.CORNER_BOTTOM_RIGHT.value)
    }
}