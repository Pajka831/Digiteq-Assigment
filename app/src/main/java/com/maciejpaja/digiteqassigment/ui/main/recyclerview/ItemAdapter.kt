package com.maciejpaja.digiteqassigment.ui.main.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maciejpaja.digiteqassigment.R

class ItemAdapter: RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var data: List<String> = arrayListOf()
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mainText: TextView

        init {
            mainText = view.findViewById(R.id.text_main)
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.mainText.text = data[position]
    }
    override fun getItemCount() = data.size

    fun submitList(list: List<String>) {
        data = list
        notifyDataSetChanged()
    }
}