package com.example.reviewapp.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.reviewapp.R
import com.example.reviewapp.domain.model.Menu
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(var images: IntArray) : SliderViewAdapter<SliderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder? {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(viewHolder: Holder, position: Int) {
        viewHolder.imageView.setImageResource(images[position])
    }

    override fun getCount(): Int {
        return images.size
    }

    class Holder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.image_view)
        }
    }

    fun setData(list: IntArray){
        images = list
        notifyDataSetChanged()
    }
}