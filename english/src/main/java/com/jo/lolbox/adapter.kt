package com.jo.lolbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jo.lolbox.databinding.ItemBinding
import java.io.Serializable
import java.util.*

class item(val name: String, val box: String, val level: Int, val point: Int) : Serializable
class recyclerViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
    var cham = binding.img
    var box = binding.box
    var level = binding.level
    var name = binding.name
    var text = binding.text
    var exp = binding.exp
}

class adapter(private val list: ArrayList<item>) : RecyclerView.Adapter<recyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return recyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: recyclerViewHolder, position: Int) {

        holder.name.text = list[position].name
       // holder.cham.setImageResource(championIconList[arrayList.indexOf(list[position].name)])
        holder.exp.text = list[position].point.toString() + "점"

      /*  if (list[position].level == 1) {
            holder.level.setImageResource(R.drawable.level1)
        }
        if (list[position].level == 2) {
            holder.level.setImageResource(R.drawable.level2)
        }
        if (list[position].level == 3) {
            holder.level.setImageResource(R.drawable.level3)
        }
        if (list[position].level == 4) {
            holder.level.setImageResource(R.drawable.level4)
        }
        if (list[position].level == 5) {
            holder.level.setImageResource(R.drawable.level5)
        }
        if (list[position].level == 6) {
            holder.level.setImageResource(R.drawable.level6)
        }
        if (list[position].level == 7) {
            holder.level.setImageResource(R.drawable.level7)
        }
        if (list[position].box == "true") {
            holder.text.text = "상자 획득 완료"
            holder.box.setImageResource(R.drawable.boxo)
        } else {
            holder.text.text = "상자 미획득"
            holder.box.setImageResource(R.drawable.boxp)
        }*/

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
