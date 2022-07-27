package com.jo.lolbox

import android.content.Context
import android.util.Log
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

class adapter(
    var list: ArrayList<item>,
    imageUrl: String,
    id: String?,
    tier: String,
    point: String,
    score: String
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var context: Context? = null
    private var imageUrl: String = imageUrl
    val id =id
    val tier = tier
    val point = point
    val score = score

    inner class Top(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon: ImageView = itemView.findViewById<View>(R.id.icon) as ImageView
        var id: TextView = itemView.findViewById<View>(R.id.id) as TextView
        var tier: TextView = itemView.findViewById<View>(R.id.tier) as TextView
        var point: TextView = itemView.findViewById<View>(R.id.point) as TextView
        var score: TextView = itemView.findViewById<View>(R.id.score) as TextView

    }

    inner class ChamList(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cham: ImageView = itemView.findViewById<View>(R.id.img) as ImageView
        var box: ImageView = itemView.findViewById<View>(R.id.box) as ImageView
        var level: ImageView = itemView.findViewById<View>(R.id.level) as ImageView
        var name: TextView = itemView.findViewById<View>(R.id.name) as TextView
        var text: TextView = itemView.findViewById<View>(R.id.text) as TextView
        var exp: TextView = itemView.findViewById<View>(R.id.exp) as TextView

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            0 -> {
                Top(
                    LayoutInflater.from(parent.context).inflate(R.layout.top, parent, false)
                )
            }

            else -> {
                ChamList(
                    LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                (holder as Top).id.text==id
                holder.id.text=id
                holder.tier.text = tier
                holder.point.text = point
                holder.score.text = score
                when {
                    tier.contains("IRON") ->  holder.icon?.setImageResource(R.drawable.iron)
                    tier.contains("BRONZE") -> holder.icon?.setImageResource(R.drawable.bronze)
                    tier.contains("SILVER") -> holder.icon?.setImageResource(R.drawable.silver)
                    tier.contains("GOLD") -> holder.icon?.setImageResource(R.drawable.gold)
                    tier.contains("PLATINUM") -> holder.icon?.setImageResource(R.drawable.platinum)
                    tier.contains("DIAMOND") -> holder.icon?.setImageResource(R.drawable.diamond)
                    tier.contains("MASTER") -> holder.icon?.setImageResource(R.drawable.master)
                    tier.contains("GRANDMASTER") -> holder.icon?.setImageResource(R.drawable.grandmaster)
                    tier.contains("CHALLENGER") -> holder.icon?.setImageResource(R.drawable.challenger)
                    else -> {
                        holder.icon?.setImageResource(R.drawable.provisional)
                        holder.tier.text = "Unranked"
                        holder.point?.text = ""
                    }
                }
            }

            else -> {
                val index = position - 1
                (holder as ChamList).name.text = list[index].koreanName
                Log.d("test",imageUrl + list[index].englishName + ".png")
                Glide.with(context!!).load(imageUrl + list[index].englishName + ".png")
                    .into(holder.cham)
                holder.exp.text = list[index].point.toString() + "점"

                if (list[index].level == 1) {
                    holder.level.setImageResource(R.drawable.level1)
                }
                if (list[index].level == 2) {
                    holder.level.setImageResource(R.drawable.level2)
                }
                if (list[index].level == 3) {
                    holder.level.setImageResource(R.drawable.level3)
                }
                if (list[index].level == 4) {
                    holder.level.setImageResource(R.drawable.level4)
                }
                if (list[index].level == 5) {
                    holder.level.setImageResource(R.drawable.level5)
                }
                if (list[index].level == 6) {
                    holder.level.setImageResource(R.drawable.level6)
                }
                if (list[index].level == 7) {
                    holder.level.setImageResource(R.drawable.level7)
                }
                if (list[index].box == "true") {
                    holder.text.text = "상자 획득 완료"
                    holder.box.setImageResource(R.drawable.boxo)
                } else {
                    holder.text.text = "상자 미획득"
                    holder.box.setImageResource(R.drawable.boxp)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size+1
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

