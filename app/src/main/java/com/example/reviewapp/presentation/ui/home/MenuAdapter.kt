package com.example.reviewapp.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reviewapp.databinding.MenuItemBinding
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.model.Menu

class MenuAdapter( var menuList: List<Menu>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(private val itemBinding: MenuItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(menu: Menu) {
            itemBinding.imgMenu.setImageResource(menu.img)
            itemBinding.txtMenuName.text = menu.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemBinding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menuList[position]
        holder.bind(menu)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    fun setData(list: List<Menu>){
        menuList = list
        notifyDataSetChanged()
    }
}