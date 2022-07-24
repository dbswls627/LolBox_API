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

import java.lang.Math.round
import java.util.*

class ViewActivity : AppCompatActivity() {
    private var mainRv: RecyclerView? = null
    var icon: ImageView?=null
    var sortButton: ImageView?=null
    var idv: TextView?=null
    var search: EditText?=null
    var tierv: TextView?=null
    var point2: TextView?=null
    var score: TextView?=null
    var items = ArrayList<item>()
    var searchList = ArrayList<item>()
    var sortList = ArrayList<item>()
    var searchHelpList = ArrayList<String>()


    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        icon = findViewById(R.id.icon)
        idv = findViewById(R.id.id)
        tierv = findViewById(R.id.tier)
        point2 = findViewById(R.id.point)
        score = findViewById(R.id.score)
        search = findViewById(R.id.search)
        sortButton = findViewById(R.id.sort)
        mainRv = findViewById(R.id.recyclerView)
        /* initiate adapter */

        mainRv!!.layoutManager = LinearLayoutManager(this)

        /* initiate recyclerview */


        items = intent.getSerializableExtra("items") as ArrayList<item>;
        items.forEach { item ->
            searchHelpList.add(item.koreanName)
        }
        val imageUrl: String? = intent.getStringExtra("imageUrl")
        mainRv!!.adapter = adapter(items, imageUrl!!)
        sortList = intent.getSerializableExtra("sort") as ArrayList<item>;
        val tier: String = intent.getStringExtra("tier").toString()
        var b: Boolean = true
        sortButton!!.setOnClickListener {
            search!!.setText("")
            if (b) {
                mainRv!!.adapter = adapter(sortList,imageUrl)
                b = false
            } else {
                mainRv!!.adapter = adapter(items,imageUrl)
                b = true
            }
        }
        search!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val searchText: String = search?.text.toString()
                searchFilter(searchText)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            private fun searchFilter(searchText: String) {
                searchList.clear()


                when (searchText) {
                    "그브" -> {
                        searchList.add(items[searchHelpList.indexOf("그레이브즈")])
                    }
                    "레나타글라스크" -> {
                        searchList.add(items[searchHelpList.indexOf("레나타 글라스크")])
                    }
                    "윌럼프" -> {
                        searchList.add(items[searchHelpList.indexOf("누누와윌럼프")])
                    }
                    "드븐" -> {
                        searchList.add(items[searchHelpList.indexOf("드레이븐")])
                    }
                    "마이" -> {
                        searchList.add(items[searchHelpList.indexOf("마스터이")])
                    }
                    "미포" -> {
                        searchList.add(items[searchHelpList.indexOf("미스포츈")])
                    }
                    "볼베" -> {
                        searchList.add(items[searchHelpList.indexOf("볼리베어")])
                    }
                    "블미" -> {
                        searchList.add(items[searchHelpList.indexOf("블라디미르")])

                    }
                    "블랭", "블크", "블츠" -> {
                        searchList.add(items[searchHelpList.indexOf("블리츠크랭크")])

                    }
                    "솔", "아우솔", "아우렐리온솔" -> {
                        searchList.add(items[searchHelpList.indexOf("아우렐리온 솔")])

                    }
                    "트타" -> {
                        searchList.add(items[searchHelpList.indexOf("트리스타나")])

                    }
                    "트페" -> {
                        searchList.add(items[searchHelpList.indexOf("트위스티드페이트")])

                    }

                    else -> {
                        items.forEach { i ->
                            if (i.koreanName.length >= searchText.length) {
                                if (i.koreanName.substring(0, searchText.length)
                                        .contains(searchText)
                                ) {
                                    searchList.add(i)
                                }
                            }
                        }
                    }
                }
                mainRv!!.adapter = adapter(searchList,imageUrl)
            }
        })

        tierv?.text = "$tier  ${intent.getStringExtra("rank")}"
        point2?.text = intent.getStringExtra("leaguePoints") + "LP"

        when (tier) {

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
        }
        idv?.text = intent.getStringExtra("name")


        val wins = intent.getIntExtra("wins",0)
        val losses = intent.getIntExtra("losses",0)
        score?.text =
            (wins!!.plus(losses!!)).toString() + "전 " + wins + "승 " + losses + "패 " + "(" + round(
                wins!! / (wins!!.plus(losses!!).toFloat()) * 100 * 10
            ) / 10f + "%)"
    }


}





