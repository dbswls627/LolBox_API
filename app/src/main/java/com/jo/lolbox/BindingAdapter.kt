package com.jo.lolbox

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(recyclerView:RecyclerView, items:LiveData<List<history>>){
        if(recyclerView.adapter == null){

            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            val adapter = historyadapter(items,MainActivity())
            recyclerView.adapter = adapter
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

