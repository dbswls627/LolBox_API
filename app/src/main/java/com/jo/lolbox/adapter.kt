package com.jo.lolbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.Serializable
import java.util.*

class item(
    val koreanName: String,
    val englishName: String,
    val key: String,
    val box: String,
    val level: Int,
    val point: Int
) : Serializable

class recyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cham: ImageView = itemView.findViewById<View>(R.id.img) as ImageView
    var box: ImageView = itemView.findViewById<View>(R.id.box) as ImageView
    var level: ImageView = itemView.findViewById<View>(R.id.level) as ImageView
    var name: TextView = itemView.findViewById<View>(R.id.name) as TextView
    var text: TextView = itemView.findViewById<View>(R.id.text) as TextView
    var exp: TextView = itemView.findViewById<View>(R.id.exp) as TextView
}

class adapter(private val list: ArrayList<item>, imageUrl: String) :
    RecyclerView.Adapter<recyclerViewHolder>() {
    private var context: Context? = null
    private var imageUrl = imageUrl
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return recyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: recyclerViewHolder, position: Int) {

        holder.name.text = list[position].koreanName
        /*holder.cham.setImageResource(championIconList[arrayList.indexOf(list[position].name)])*/
        Glide.with(context!!).load(imageUrl + list[position].englishName + ".png")
            .into(holder.cham)
        holder.exp.text = list[position].point.toString() + "점"

        if (list[position].level == 1) {
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
            }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}