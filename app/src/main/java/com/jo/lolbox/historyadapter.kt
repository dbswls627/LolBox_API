package com.jo.lolbox

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData

import java.util.*

class recyclerViewHolder2(v : View) : RecyclerView.ViewHolder(v){

    var his: TextView = v.findViewById(R.id.history)
    var del: ImageButton = v.findViewById(R.id.del)
}
class historyadapter(private val list: LiveData<List<history>>, var listener:ItemListener) : RecyclerView.Adapter<recyclerViewHolder2>() {
    interface ItemListener {
        fun onItemDel(name: String)
        fun onItemSelect(name: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder2 {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.history, parent, false)
        return recyclerViewHolder2(view)
    }

    override fun onBindViewHolder(holder: recyclerViewHolder2, position: Int) {

        holder.his?.text= list.value!![position].s

        holder.del.setOnClickListener {
            listener?.onItemDel(list.value!![position].s)
        }
        holder.his.setOnClickListener {
            listener?.onItemSelect(list.value!![position].s)
        }
    }

    override fun getItemCount(): Int {
        return try {
            list.value!!.size
        }catch (e:Exception){
            0
        }
    }

}