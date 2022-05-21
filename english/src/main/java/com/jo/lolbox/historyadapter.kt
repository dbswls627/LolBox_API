package com.jo.lolbox

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.jo.lolbox.databinding.HistoryBinding
import com.jo.lolbox.databinding.ItemBinding

import java.util.*

class recyclerViewHolder2(private val binding:HistoryBinding ) : RecyclerView.ViewHolder(binding.root){


    var his = binding.history
    var del = binding.del
}
class historyadapter(private val list: ArrayList<history>, val context: Context) : RecyclerView.Adapter<recyclerViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder2 {

        val binding = HistoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return recyclerViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: recyclerViewHolder2, position: Int) {
        var db: AppDatabase = AppDatabase.getInstance(context)!!
        holder.his?.text=list[position].s
        holder.del.setOnClickListener {
            db.historyDao().delete(list[position])
            (context as MainActivity).notifyDataSetChanged()
        }
        holder.his.setOnClickListener {
            (context as MainActivity).setIntent(list[position].s,list[position].nation)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}