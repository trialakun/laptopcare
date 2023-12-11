package com.example.laptopcarebeta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeInfoLaptopAdapter(var infolaptopList: ArrayList<Modelinfolaptop>) : RecyclerView.Adapter<HomeInfoLaptopAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val namainfolaptop: TextView = itemView.findViewById(R.id.HomeNamaInfoLaptopTextView)
        val desinfolaptop: TextView = itemView.findViewById(R.id.HomeDeskripsiInfoLaptopTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_info_laptop, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return infolaptopList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val infolaptop = infolaptopList[position]

        holder.namainfolaptop.text = infolaptop.namainfolaptop
        holder.desinfolaptop.text = infolaptop.desinfolaptop
    }
}