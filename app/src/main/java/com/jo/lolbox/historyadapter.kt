package com.jo.lolbox

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide

import java.util.*

class recyclerViewHolder2(v : View) : RecyclerView.ViewHolder(v){

    var his: TextView = v.findViewById(R.id.history)
    var del: ImageButton = v.findViewById(R.id.del)
    var historyIcon: ImageView = v.findViewById(R.id.historyIcon)
}
class historyadapter(private val list: LiveData<List<history>>, var listener:ItemListener) : RecyclerView.Adapter<recyclerViewHolder2>() {
    lateinit var context:Context
    interface ItemListener {
        fun onItemDel(name: String)
        fun onItemSelect(name: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder2 {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history, parent, false)
        return recyclerViewHolder2(view)
    }

    override fun onBindViewHolder(holder: recyclerViewHolder2, position: Int) {

        Glide.with(context!!).load("http://ddragon.leagueoflegends.com/cdn/12.13.1/img/profileicon/"+ list.value?.get(position)!!.profileIconId+".png")
            .into(holder.historyIcon)
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