package com.jo.lolbox

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

import java.util.*

class recyclerViewHolder2(v : View) : RecyclerView.ViewHolder(v){


    var his: TextView = v.findViewById(R.id.history)
    var del: ImageButton = v.findViewById(R.id.del)
}
class historyadapter(private val list: ArrayList<history>, val context: Context) : RecyclerView.Adapter<recyclerViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder2 {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.history, parent, false)
        return recyclerViewHolder2(view)
    }

    override fun onBindViewHolder(holder: recyclerViewHolder2, position: Int) {
        var db: AppDatabase = AppDatabase.getInstance(context)!!
        holder.his?.text=list[position].s
        holder.del.setOnClickListener {
            db.historyDao().delete(list[position])
            (context as MainActivity).notifyDataSetChanged()
        }
        holder.his.setOnClickListener {
            (context as MainActivity).setIntent(list[position].s)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}