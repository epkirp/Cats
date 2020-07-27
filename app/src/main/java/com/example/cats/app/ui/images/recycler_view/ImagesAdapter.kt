package com.example.cats.app.ui.images.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.R
import com.example.model.CatImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*


class ImagesAdapter(
    private var images: ArrayList<CatImage>,
    private var callback: Callback
) : RecyclerView.Adapter<ImagesAdapter.ImageHolder>() {

    interface Callback {
        fun onImageClick(image: CatImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_image, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.onBind(images[position])
    }

    inner class ImageHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.itemImageView.setOnClickListener {
                callback.onImageClick(images[adapterPosition])
            }
        }

        fun onBind(item: CatImage) {
            Picasso.get().load(item.url)
                .fit()
                .error(R.mipmap.image_holder)
                .into(view.itemImageView, object : com.squareup.picasso.Callback {

                    override fun onSuccess() {
                        if (view.progressBar != null) {
                            view.progressBar.visibility = View.GONE
                        }
                    }

                    override fun onError(e: Exception?) {
                    }
                })
        }
    }

}