package com.jo.lolbox
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jo.json.R
import org.json.JSONObject
import org.json.JSONException
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    var t : EditText?=null
    private var rv : RecyclerView?=null
    lateinit var db: AppDatabase

    private val key = "RGAPI-92235ea0-30bc-4644-ae14-7851cfa60ecd"
    private val idAddress = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
    override fun onRestart() {
        notifyDataSetChanged()
        super.onRestart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        db = AppDatabase.getInstance(this)!!

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        t = findViewById(R.id.text)
        rv = findViewById(R.id.history_rv)

        notifyDataSetChanged()
       t!!.imeOptions=EditorInfo.IME_ACTION_DONE
        t!!.setOnEditorActionListener { v, actionId, event ->
            if (actionId==EditorInfo.IME_ACTION_DONE){
                setIntent(t!!.text.toString())
                true
            }
            else false
        }
    }
    fun setIntent(url :String) {
        val intent = Intent(this, ViewActivity::class.java)
        object : Thread() {
            override fun run() {
        var id: String? = null
        var i: String? = null
        var name: String? = null
        val urlIdAddress = "$idAddress$url?api_key=$key"
        try {
            val url1 = URL(urlIdAddress).readText()
            val obj = JSONObject(url1)
            id = obj.getString("id")
            name = obj.getString("name")
            i = obj.getString("profileIconId")
            intent.putExtra("nameKey", id)
            intent.putExtra("name", name)
            intent.putExtra("i", i)
            db.historyDao().insert(history(name))
            startActivity(intent)
        }catch (e: MalformedURLException) {
            e.printStackTrace()
        }catch (e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "존재하지 않는 소환사명입니다.", Toast.LENGTH_SHORT).show()
                }

                e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
            }

        }.start()
    }
    fun notifyDataSetChanged(){
        var historyList=ArrayList<history>()
        for(i in db.historyDao().get()){
            historyList.add(i)
        }
        historyList.reverse()
        rv = findViewById(R.id.history_rv)
        val mRecyclerAdapter = historyadapter(historyList,this)
        rv?.adapter = mRecyclerAdapter

        rv?.layoutManager = LinearLayoutManager(this)
        mRecyclerAdapter.notifyDataSetChanged()
    }
}