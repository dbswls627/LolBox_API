package com.jo.lolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jo.json.R
import org.json.JSONArray
import org.json.JSONException
import android.view.View
import java.io.IOException
import java.lang.Math.round
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList

class ViewActivity : AppCompatActivity() {
    private val boxAddress = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/"
    private val tierAddress = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/"
    private var mainRv: RecyclerView? = null
    private var searchRv: RecyclerView? =null
    private var sortRv: RecyclerView? =null
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
    val arrayID = arrayListOf(
        86,
        3,
        41,
        79,
        104,
        887,
        150,
        267,
        75,
        111,
        56,
        20,
        76,
        518,
        122,
        131,
        119,
        13,
        497,
        33,
        99,
        68,
        58,
        89,
        421,
        526,
        107,
        236,
        117,
        7,
        64,
        92,
        127,
        876,
        11,
        57,
        90,
        54,
        82,
        25,
        36,
        21,
        432,
        110,
        254,
        45,
        67,
        711,
        161,
        106,
        201,
        63,
        8,
        53,
        234,
        112,
        78,
        360,
        14,
        517,
        35,
        235,
        147,
        113,
        875,
        37,
        16,
        98,
        102,
        50,
        72,
        15,
        5,
        134,
        27,
        412,
        103,
        32,
        136,
        427,
        268,
        84,
        166,
        266,
        523,
        12,
        1,
        34,
        22,
        157,
        245,
        60,
        62,
        516,
        61,
        2,
        777,
        83,
        77,
        6,
        19,
        350,
        39,
        28,
        81,
        420,
        59,
        498,
        143,
        154,
        40,
        24,
        238,
        101,
        126,
        142,
        115,
        202,
        26,
        222,
        31,
        43,
        164,
        38,
        30,
        69,
        145,
        121,
        55,
        429,
        85,
        51,
        141,
        10,
        96,
        42,
        133,
        240,
        246,
        203,
        44,
        91,
        163,
        223,
        48,
        18,
        23,
        4,
        29,
        17,
        555,
        80,
        9,
        114,
        105,
        74,
        120
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        icon = findViewById(R.id.icon)
        idv = findViewById(R.id.id)
        tierv = findViewById(R.id.tier)
        point2 = findViewById(R.id.point)
        score = findViewById(R.id.score)
        search = findViewById(R.id.search)
        sortButton=findViewById(R.id.sort)
        mainRv = findViewById(R.id.recyclerView)
        searchRv = findViewById(R.id.recyclerView2)
        sortRv = findViewById(R.id.recyclerView3)
        /* initiate adapter */
        val mRecyclerAdapter = adapter(items)
        val mRecyclerAdapter2 = adapter(searchList)
        val mRecyclerAdapter3 = adapter(sortList)
        mainRv!!.layoutManager = LinearLayoutManager(this)
        searchRv!!.layoutManager = LinearLayoutManager(this)
        sortRv!!.layoutManager = LinearLayoutManager(this)
        /* initiate recyclerview */
        mainRv!!.adapter = mRecyclerAdapter
        searchRv!!.adapter = mRecyclerAdapter2
        sortRv!!.adapter = mRecyclerAdapter3
        sortRv!!.visibility=View.GONE
        var b:Boolean=true
        sortButton!!.setOnClickListener {
            search!!.setText("")
            if (b) {
                sortRv!!.visibility=View.VISIBLE
                mainRv!!.visibility=View.GONE
                b=false
            }
            else{
                mainRv!!.visibility=View.VISIBLE
                sortRv!!.visibility=View.GONE
                b=true
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
                mainRv!!.visibility = View.GONE
                sortRv!!.visibility = View.GONE
                searchRv!!.adapter = mRecyclerAdapter2
                when (searchText) {
                    "그브" -> {
                        searchList.add(items[adapter.arrayList.indexOf("그레이브즈")])
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
                    ""->{
                        mainRv!!.visibility = View.VISIBLE
                    }
                    else -> {
                        for (i in 0 until items.size) {
                            if (items[i].name.length >= searchText.length) {
                                if (items[i].name.substring(0, searchText.length)
                                        .contains(searchText)
                                ) {
                                    searchList.add(items[i])
                                }
                            }
                        }
                    }
                }
            }
        })
        var id = intent.getStringExtra("nameKey")
        var i = intent.getStringExtra("i")
        var name = intent.getStringExtra("name")

        object : Thread() {
            override fun run() {
                var tier: String? = null
                var rank: String? = null
                var point: String? = null
                var wins: Int? = null
                var losses: Int? = null
                items.clear()
                try {
                    val urlTierAddress = tierAddress+id+"?api_key="+resources.getString(R.string.key)
                    val urlBoxAddress =  boxAddress+id+"?api_key="+resources.getString(R.string.key)
                    val url1 = URL(urlBoxAddress).readText()
                    val url2 = URL(urlTierAddress).readText()
                    val jsonArray1 = JSONArray(url1)
                    val jsonArray2 = JSONArray(url2)

                    if (jsonArray2.toString() != "[]") {
                        for (i in 0 until jsonArray2.length()) {
                            var temp = jsonArray2.getJSONObject(i)
                            if (temp.getString("queueType") == "RANKED_SOLO_5x5") {
                                tier = temp.getString("tier")
                                rank = temp.getString("rank")
                                point = temp.getString("leaguePoints")
                                wins = temp.getInt("wins")
                                losses = temp.getInt("losses")
                            }
                        }
                    }
                    for (i in 0 until adapter.arrayList.size) {
                        items.add(item(adapter.arrayList[i], "false", 0, 0))
                        sortList.add(item(adapter.arrayList[i], "false", 0, 0))
                    }

                    for (i in 0 until jsonArray1.length()) {
                        val temp = jsonArray1.getJSONObject(i)
                        val id1 = temp.getInt("championId")
                        val b = temp.getString("chestGranted")
                        val l = temp.getInt("championLevel")
                        val p = temp.getInt("championPoints")
                        items[arrayID.indexOf(id1)] =
                            item(adapter.arrayList[arrayID.indexOf(id1)], b, l, p)
                    }
                    items.sortWith(compareBy({ -it.level }, { -it.point }))
                    for (i in 0 until items.size) {
                        sortList[i] = items[i]
                    }
                    items.sortWith(compareBy{it.name})


                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }catch (e :ArrayIndexOutOfBoundsException){   // 신챔프 추가 안되었을 시
                    e.printStackTrace()
                }finally {
                    runOnUiThread {
                        if (tier != null) {
                            if (tier == "IRON") {
                                icon?.setImageResource(R.drawable.iron)
                            }
                            if (tier == "BRONZE") {
                                icon?.setImageResource(R.drawable.bronze)
                            }
                            if (tier == "SILVER") {
                                icon?.setImageResource(R.drawable.silver)
                            }
                            if (tier == "GOLD") {
                                icon?.setImageResource(R.drawable.gold)
                            }
                            if (tier == "PLATINUM") {
                                icon?.setImageResource(R.drawable.platinum)
                            }
                            if (tier == "DIAMOND") {
                                icon?.setImageResource(R.drawable.diamond)
                            }
                            if (tier == "MASTER") {
                                icon?.setImageResource(R.drawable.master)
                            }
                            if (tier == "GRANDMASTER") {
                                icon?.setImageResource(R.drawable.grandmaster)
                            }
                            if (tier == "CHALLENGER") {
                                icon?.setImageResource(R.drawable.challenger)
                            }
                            tierv?.text = "$tier  $rank"
                            idv?.text = name
                            point2?.text = point + "LP"
                            score?.text =
                                (wins!!.plus(losses!!)).toString() + "전 " + wins + "승 " + losses + "패 " + "(" + round(
                                    wins!! / (wins!!.plus(losses!!).toFloat()) * 100 * 10
                                ) / 10f + "%)"
                        }
                        if (tier == null) {
                            icon?.setImageResource(R.drawable.provisional)
                            idv?.text = name
                            tierv?.text = "Unranked"
                        }

                        mRecyclerAdapter.notifyDataSetChanged()
                         mRecyclerAdapter3.notifyDataSetChanged()
                    }

                }
            }
        }.start()
    }

}


