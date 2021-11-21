package com.jo.lolbox

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jo.json.R

class SearchFragment : Fragment() {
    var items=ArrayList<item>()
    var mRecyclerView2: RecyclerView? =null
  /*  override fun onAttach(context: Context) {
        super.onAttach(context)
        items = (context as ViewActivity).getSearchItems()
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 처리
        return inflater.inflate(R.layout.fragment_search, null)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView2 = view.findViewById(R.id.searchRv)
        mRecyclerView2!!.layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView2!!.adapter=adapter(items)
    }
}