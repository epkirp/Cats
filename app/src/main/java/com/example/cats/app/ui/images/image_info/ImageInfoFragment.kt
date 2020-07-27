package com.example.cats.app.ui.images.image_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cats.R
import com.example.cats.R.layout
import com.example.model.Breed
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_breed_info.*
import kotlinx.android.synthetic.main.fragment_breed_info.view.*

class ImageInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.fragment_breed_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        infoToolbar.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        val bundle: Bundle? = this.arguments

        Picasso.get().load(bundle?.getString("url")).fit()

            .error(R.mipmap.image_holder)
            .into(view.infoImageView)

        val breed = bundle?.getSerializable("breed") as Breed

        textName.text = breed.name
        textDescription.text = breed.description
        textTemperament.text = breed.temperament
        textLifeSpan.text = breed.lifeSpan

    }
}