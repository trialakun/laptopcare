package com.example.laptopcarebeta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class HomeInfoLaptopAdapter(var infolaptopList: ArrayList<Modelinfolaptop>) : RecyclerView.Adapter<HomeInfoLaptopAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val namainfolaptop: TextView = itemView.findViewById(R.id.HomeNamaInfoLaptopTextView)
        val desinfolaptop: TextView = itemView.findViewById(R.id.HomeDeskripsiInfoLaptopTextView)
        val btnshowdetail: LinearLayout = itemView.findViewById(R.id.btnlistinfolaptoplinearlayout)
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
        holder.btnshowdetail.setOnClickListener {
            val context = holder.itemView.context
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager // Jika fragment di-host oleh AppCompatActivity

            val detailInfoLaptopFragment = DetailInfoLaptopFragment()
            val bundle = Bundle()
            bundle.putParcelable("info_laptop", infolaptop)
            detailInfoLaptopFragment.arguments = bundle

            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView1, detailInfoLaptopFragment) // Ganti R.id.fragment_container dengan ID dari container fragment Anda
                .addToBackStack(null) // Jika Anda ingin menambahkan transaksi ke back stack
                .commit()
        }
    }
}