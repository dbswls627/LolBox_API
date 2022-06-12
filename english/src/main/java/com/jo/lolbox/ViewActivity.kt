package com.jo.lolbox

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.jo.lolbox.databinding.ActivityViewBinding
import java.lang.Math.round
import java.util.*

private lateinit var binding: ActivityViewBinding

class ViewActivity : AppCompatActivity() {
    var items = ArrayList<item>()
    var searchList = ArrayList<item>()
    var sortList = ArrayList<item>()
    lateinit var imageUrl:String

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* initiate adapter */

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        /* initiate recyclerview */


        items = intent.getSerializableExtra("items") as ArrayList<item>;
        imageUrl = intent.getStringExtra("imageUrl").toString()

        binding.recyclerView!!.adapter = adapter(items,imageUrl)
        sortList = intent.getSerializableExtra("sort") as ArrayList<item>;
        val tier: String = intent.getStringExtra("tier").toString()
        var b: Boolean = true
        /*sortButton!!.setOnClickListener {
            search!!.setText("")
            if (b) {
                mainRv!!.adapter = adapter(sortList)
                b = false
            } else {
                mainRv!!.adapter = adapter(items)
                b = true
            }
        }*/
        binding.search!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val searchText: String = binding.search?.text.toString()
                searchFilter(searchText)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            private fun searchFilter(searchText: String) {
                searchList.clear()

                items.forEach { i ->
                    if (i.name.length >= searchText.length) {
                        if (i.name.substring(0, searchText.length)
                                .equals(searchText,true)
                        ) {
                            searchList.add(i)
                        }
                    }
                }
                binding.recyclerView!!.adapter = adapter(searchList,imageUrl)
            }
        })

        binding.tier?.text = "$tier  ${intent.getStringExtra("rank")}"
        binding.point?.text = intent.getStringExtra("leaguePoints") + "LP"

        when (tier) {

            "IRON" -> {
                binding.icon?.setImageResource(R.drawable.iron)
            }
            "BRONZE" -> {
                binding.icon?.setImageResource(R.drawable.bronze)
            }
            "SILVER" -> {
                binding.icon?.setImageResource(R.drawable.silver)
            }
            "GOLD" -> {
                binding.icon?.setImageResource(R.drawable.gold)
            }
            "PLATINUM" -> {
                binding.icon?.setImageResource(R.drawable.platinum)
            }
            "DIAMOND" -> {
                binding.icon?.setImageResource(R.drawable.diamond)
            }
            "MASTER" -> {
                binding.icon?.setImageResource(R.drawable.master)
            }
            "GRANDMASTER" -> {
                binding.icon?.setImageResource(R.drawable.grandmaster)
            }
            "CHALLENGER" -> {
                binding.icon?.setImageResource(R.drawable.challenger)
            }
            else -> {
                binding.icon?.setImageResource(R.drawable.provisional)
                binding.tier?.text = "Unranked"
                binding.point?.text = ""
            }
        }
        binding.id?.text = intent.getStringExtra("name")


        val wins = intent.getIntExtra("wins",0)
        val losses = intent.getIntExtra("losses",0)
        binding.score.text =
            (wins!!.plus(losses!!)).toString() + "전 " + wins + "승 " + losses + "패 " + "(" + round(
                wins!! / (wins!!.plus(losses!!).toFloat()) * 100 * 10
            ) / 10f + "%)"
    }


}





