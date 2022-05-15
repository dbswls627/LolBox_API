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


    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* initiate adapter */

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        /* initiate recyclerview */


        items = intent.getSerializableExtra("items") as ArrayList<item>;
        binding.recyclerView!!.adapter = adapter(items)
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


        /*        when (searchText) {
                    "그브" -> {
                        searchList.add(items[adapter.arrayList.indexOf("그레이브즈")])
                    }
                    "레나타글라스크" -> {
                        searchList.add(items[adapter.arrayList.indexOf("레나타 글라스크")])
                    }
                    "윌럼프" -> {
                        searchList.add(items[adapter.arrayList.indexOf("누누와윌럼프")])
                    }
                    "드븐" -> {
                        searchList.add(items[adapter.arrayList.indexOf("드레이븐")])
                    }
                    "마이" -> {
                        searchList.add(items[adapter.arrayList.indexOf("마스터이")])
                    }
                    "미포" -> {
                        searchList.add(items[adapter.arrayList.indexOf("미스포츈")])
                    }
                    "볼베" -> {
                        searchList.add(items[adapter.arrayList.indexOf("볼리베어")])
                    }
                    "블미" -> {
                        searchList.add(items[adapter.arrayList.indexOf("블라디미르")])

                    }
                    "블랭", "블크", "블츠" -> {
                        searchList.add(items[adapter.arrayList.indexOf("블리츠크랭크")])

                    }
                    "솔", "아우솔", "아우렐리온솔" -> {
                        searchList.add(items[adapter.arrayList.indexOf("아우렐리온 솔")])

                    }
                    "트타" -> {
                        searchList.add(items[adapter.arrayList.indexOf("트리스타나")])

                    }
                    "트페" -> {
                        searchList.add(items[adapter.arrayList.indexOf("트위스티드페이트")])

                    }

                    else -> {
                        items.forEach { i ->
                            if (i.name.length >= searchText.length) {
                                if (i.name.substring(0, searchText.length)
                                        .contains(searchText)
                                ) {
                                    searchList.add(i)
                                }
                            }
                        }
                    }
                }*/
                binding.recyclerView!!.adapter = adapter(searchList)
            }
        })

        binding.tier?.text = "$tier  ${intent.getStringExtra("rank")}"
        binding.point?.text = intent.getStringExtra("leaguePoints") + "LP"

       /* when (tier) {

            "IRON" -> {
                icon?.setImageResource(R.drawable.iron)
            }
            "BRONZE" -> {
                icon?.setImageResource(R.drawable.bronze)
            }
            "SILVER" -> {
                icon?.setImageResource(R.drawable.silver)
            }
            "GOLD" -> {
                icon?.setImageResource(R.drawable.gold)
            }
            "PLATINUM" -> {
                icon?.setImageResource(R.drawable.platinum)
            }
            "DIAMOND" -> {
                icon?.setImageResource(R.drawable.diamond)
            }
            "MASTER" -> {
                icon?.setImageResource(R.drawable.master)
            }
            "GRANDMASTER" -> {
                icon?.setImageResource(R.drawable.grandmaster)
            }
            "CHALLENGER" -> {
                icon?.setImageResource(R.drawable.challenger)
            }
            else -> {
                icon?.setImageResource(R.drawable.provisional)
                tierv?.text = "Unranked"
                point2?.text = ""
            }
        }*/
        binding.id?.text = intent.getStringExtra("name")


        val wins = intent.getIntExtra("wins",0)
        val losses = intent.getIntExtra("losses",0)
        binding.score.text =
            (wins!!.plus(losses!!)).toString() + "전 " + wins + "승 " + losses + "패 " + "(" + round(
                wins!! / (wins!!.plus(losses!!).toFloat()) * 100 * 10
            ) / 10f + "%)"
    }


}





