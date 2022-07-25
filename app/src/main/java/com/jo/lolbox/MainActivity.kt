package com.jo.lolbox
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jo.lolbox.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() ,historyadapter.ItemListener {
    var t: EditText? = null
    var id: String? = null
    var idSuccess = true
    var items = ArrayList<item>()
    var sortList = ArrayList<item>()
    var url: String? = null
    lateinit var imageUrl:String

    var englishNameArray = ArrayList<String>()
    var koreanNameArray = ArrayList<String>()
    var keyArray = ArrayList<String>()
    private val idAddress = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
    private val boxAddress = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/"
    private val tierAddress = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/"

    lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this,)[MyViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel=viewModel
        t = findViewById(R.id.text)

        val mRecyclerAdapter = historyadapter(viewModel.list, this)
        binding.historyRv?.adapter = mRecyclerAdapter
        binding.historyRv?.layoutManager = LinearLayoutManager(this)
        mRecyclerAdapter.notifyDataSetChanged()

        t!!.imeOptions = EditorInfo.IME_ACTION_DONE
        t!!.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setIntent(t!!.text.toString())
                true
            } else false
        }

        var thread = Thread {

            var doc = Jsoup.connect("https://developer.riotgames.com/docs/lol").get()
            var elements = doc.select("div.content p a")
            url = elements[18].text().replace("en_US","ko_KR")

            imageUrl = elements[23].text().replace("Aatrox.png","")
            val url1 = URL(url).readText()
            var obj = JSONObject(url1)
            obj = obj.getJSONObject("data")
            val championName = obj.names()
            Log.d("test",obj.getJSONObject(championName[1].toString()).getString("name"))
            for (i in 0 until championName.length()) {
                englishNameArray.add(championName[i].toString())
                koreanNameArray.add(obj.getJSONObject(championName[i].toString()).getString("name"))
                keyArray.add(obj.getJSONObject(englishNameArray[i]).getString("key"))
            }
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


    private fun searchID(name: String, intent: Intent) {

        val urlIdAddress =
            idAddress + name + "?api_key=" + resources.getString(R.string.key)
        Log.d("test",urlIdAddress)
        try {
            val url1 = URL(urlIdAddress).readText()
            val obj = JSONObject(url1)
            id = obj.getString("id")
            val profileIconId = obj.getString("profileIconId")
            intent.putExtra("name", name)
            viewModel.delete(name)
            viewModel.insert(name,profileIconId)

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
                tierAddress + id + "?api_key=" + resources.getString(R.string.key)
            val urlBoxAddress =
                boxAddress + id + "?api_key=" + resources.getString(R.string.key)

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
            koreanNameArray.forEachIndexed() { index, i ->
                items.add(item(i,englishNameArray[index],keyArray[index], "false", 0, 0))
                sortList.add(item(i,englishNameArray[index],keyArray[index], "false", 0, 0))
            }


            for (i in 0 until jsonArray1.length()) {
                val temp = jsonArray1.getJSONObject(i)
                val id1 = temp.getString("championId")
                val b = temp.getString("chestGranted")
                val l = temp.getInt("championLevel")
                val p = temp.getInt("championPoints")
                items[keyArray.indexOf(id1)] =
                    item(koreanNameArray[keyArray.indexOf(id1)],englishNameArray[keyArray.indexOf(id1)],id1,b, l, p)
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
            items.sortWith(compareBy { it.koreanName })
            intent.putExtra("sort", sortList)
            intent.putExtra("items", items)
            intent.putExtra("imageUrl",imageUrl)

        }
    }

    override fun onItemDel(name: String) {
        viewModel.delete(name)
    }

    override fun onItemSelect(name: String) {
        setIntent(name)
    }

}

