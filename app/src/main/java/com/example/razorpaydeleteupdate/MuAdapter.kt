package com.example.razorpaydeleteupdate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface ClickListener{
    fun delete(id :String,position: Int)
}

class MyAdapter(val context: Context,val listItem : List<Item>,val listener: ClickListener) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val getName : TextView = itemView.findViewById(R.id.getName)
        val getAmount : TextView =itemView.findViewById(R.id.getAmout)
        val getDes : TextView = itemView.findViewById(R.id.getDes)
        val moreHori : ImageView = itemView.findViewById(R.id.moreHori)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_desing_for_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p0 = listItem[position]
        holder.getDes.text=p0.description
        holder.getName.text=p0.name
        holder.getAmount.text=p0.amount.toString()

        holder.moreHori.setOnClickListener {
            listener.delete(p0.id.toString(),position)
        }
    }
}