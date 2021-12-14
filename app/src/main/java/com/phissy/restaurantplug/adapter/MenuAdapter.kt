package com.phissy.restaurantplug.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phissy.restaurantplug.R
import com.phissy.restaurantplug.repository.model.Menu

class MenuAdapter(val context: Context, val menuList: ArrayList<Menu>,
val onMenuClickListener: OnMenuClickListener) :
    RecyclerView.Adapter<MenuAdapter.MenuHolder>() {

    private var layoutInflater = LayoutInflater.from(context)

    class MenuHolder(val item: View) : RecyclerView.ViewHolder(item) {

        var imageView: ImageView
        var textViewMenuName: TextView
        var textViewDes: TextView
        var checkBox: CheckBox
        var textViewPrice: TextView

        init {
            imageView = item.findViewById(R.id.imageViewRowMenu)
            textViewMenuName = item.findViewById(R.id.textViewMenuRowMenu)
            textViewDes = item.findViewById(R.id.textViewDescriptionRowMenu)
            checkBox = item.findViewById(R.id.checkBoxRowMenu)
            textViewPrice = item.findViewById(R.id.textViewPriceRowMenu)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        return MenuHolder(layoutInflater.inflate(R.layout.row_menu, parent, false))
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        val menu = menuList[position]
        Glide.with(context)
            .load(menu.imageUrl)
            .centerCrop()
            .into(holder.imageView)
        holder.textViewMenuName.text = menu.name
        holder.textViewPrice.text = "${"$"}${menu.price}"
        holder.textViewDes.text = menu.description
        holder.checkBox.isChecked = menu.isChecked
        holder.checkBox.setOnClickListener {
            onMenuClickListener.onMenuClick(position)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    interface OnMenuClickListener {
        fun onMenuClick(position: Int)
    }

}