package com.jo.lolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jo.lolbox.databinding.ActivityMainBinding
import org.json.JSONObject
import org.jsoup.Jsoup
import java.net.URL

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    var url: String? = null
    var nameArray = ArrayList<String>()
    var keyArray = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            binding.textview.text= nameArray[1]+":"+keyArray[1]
        }
        thread.start()
        thread.join()

    }
}