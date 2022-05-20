package com.jo.lolbox

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jo.lolbox.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var nation :String = "kr"
    lateinit var db: AppDatabase
    var id:String? = null
    var idSuccess = true
    val https :String ="https://"
    private val idAddress = ".api.riotgames.com/lol/summoner/v4/summoners/by-name/"
    private val boxAddress = ".api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/"
    private val tierAddress = ".api.riotgames.com/lol/league/v4/entries/by-summoner/"
    var url: String? = null
    var items = java.util.ArrayList<item>()
    var sortList = java.util.ArrayList<item>()
    var nameArray = ArrayList<String>()
    var keyArray = ArrayList<String>()
    override fun onRestart() {
        notifyDataSetChanged()
        super.onRestart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!

        notifyDataSetChanged()


        binding.text!!.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.spinner.adapter = ArrayAdapter.createFromResource(this,R.array.spinnerItem,android.R.layout.simple_spinner_dropdown_item)
        binding.text!!.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setIntent(binding.text!!.text.toString())
                true
            } else false
        }

        var thread = Thread {

            var doc = Jsoup.connect("https://developer.riotgames.com/docs/lol").get()
            var elements = doc.select("div.content p a")
            url = elements[18].text()
            val url1 = URL(url).readText()
            var obj = JSONObject(url1)
            obj = obj.getJSONObject("data")
            val championName = obj.names()

            for (i in 0 until championName.length()) {
                nameArray.add(championName[i].toString())
                keyArray.add(obj.getJSONObject(nameArray[i]).getString("key"))
            }

            Log.d("테스트", obj.getJSONObject(nameArray[1]).getString("key"))
            Log.d("테스트", keyArray[0])
            searchID("대통령댁",intent)
        }
        thread.start()

    }

    fun setIntent(name: String) {
        idSuccess = true
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.loding_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(false)

        dialog.show()
        items.clear()
        val intent = Intent(this, ViewActivity::class.java)
        var cancel = false
        val thread = Thread {

            searchID(name, intent)
            if (idSuccess) {
                searchData(intent)
            }
            dialog.dismiss()

            if (!cancel && idSuccess) {
                startActivity(intent)
            }


        }

        thread.start()
        dialog.setOnCancelListener {
            thread.interrupt()
            cancel = true
        }

    }
    fun notifyDataSetChanged(){
        var historyList= java.util.ArrayList<history>()
        for(i in db.historyDao().get()){
            historyList.add(i)
        }
        historyList.reverse()

        val mRecyclerAdapter = historyadapter(historyList, this)
        binding.historyRv?.adapter = mRecyclerAdapter

        binding.historyRv?.layoutManager = LinearLayoutManager(this)
        mRecyclerAdapter.notifyDataSetChanged()
    }

    private fun searchID(name: String, intent: Intent) {

        val urlIdAddress =
            https+nation+idAddress + name + "?api_key=" + resources.getString(R.string.key)

        try {
            val url1 = URL(urlIdAddress).readText()
            val obj = JSONObject(url1)
            id = obj.getString("id")
            intent.putExtra("name", name)
            db.historyDao().insert(history(name))

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            runOnUiThread {
                Toast.makeText(this@MainActivity, "존재하지 않는 소환사명입니다.", Toast.LENGTH_SHORT)
                    .show()
                idSuccess = false
            }

            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun searchData(intent: Intent) {
        try {
            val urlTierAddress =
                https+nation+tierAddress + id + "?api_key=" + resources.getString(R.string.key)
            val urlBoxAddress =
                https+nation+boxAddress + id + "?api_key=" + resources.getString(R.string.key)

            val url1 = URL(urlBoxAddress).readText()
            val url2 = URL(urlTierAddress).readText()

            val jsonArray1 = JSONArray(url1)
            val jsonArray2 = JSONArray(url2)

            if (jsonArray2.toString() != "[]") {

                for (i in 0 until jsonArray2.length()) {

                    var temp = jsonArray2.getJSONObject(i)

                    if (temp.getString("queueType") == "RANKED_SOLO_5x5") {
                        intent.putExtra("tier", temp.getString("tier"))
                        intent.putExtra("rank", temp.getString("rank"))
                        intent.putExtra("leaguePoints", temp.getString("leaguePoints"))
                        intent.putExtra("wins", temp.getString("wins").toInt())
                        intent.putExtra("losses", temp.getString("losses").toInt())
                        break;
                    }
                }
            }
            nameArray.forEach { i ->
                items.add(item(i, "false", 0, 0))
                sortList.add(item(i, "false", 0, 0))
            }

            for (i in 0 until jsonArray1.length()) {
                val temp = jsonArray1.getJSONObject(i)
                val id1 = temp.getString("championId")
                val b = temp.getString("chestGranted")
                val l = temp.getInt("championLevel")
                val p = temp.getInt("championPoints")
                items[keyArray.indexOf(id1)] =
                    item(nameArray[keyArray.indexOf(id1)], b, l, p)
            }


        } catch (e: MalformedURLException) {
            Log.d("catch", "MalformedURLException")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d("catch", "IOException")
            e.printStackTrace()
        } catch (e: JSONException) {
            Log.d("catch", "JSONException")
            e.printStackTrace()
        } catch (e: ArrayIndexOutOfBoundsException) {   // 신챔프 추가 안되었을 시
            Log.d("catch", "ArrayIndexOutOfBoundsException")
            e.printStackTrace()
        } finally {
            items.sortWith(compareBy({ -it.level }, { -it.point }))
            items.forEachIndexed { index, value ->
                sortList[index] = value
            }
            intent.putExtra("sort", sortList)
            items.sortWith(compareBy { it.name })

            intent.putExtra("items", items)


        }
    }
}