package com.example.calculatorapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R


class RecyclerViewAdapter(val listData: ArrayList<DataModel>, val clickListener: ClickListener):RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        var titleTextView: TextView
        init {
            titleTextView = view.findViewById(R.id.titleTextView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view =  LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleTextView.text = listData.get(position).title
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(listData.get(position))
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    interface ClickListener{
        fun onItemClick(dataModel: DataModel)
    }
}