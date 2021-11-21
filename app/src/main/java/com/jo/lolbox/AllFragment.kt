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

class AllFragment : Fragment() {
    var items=ArrayList<item>()
    var mRecyclerView: RecyclerView? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        items = (context as ViewActivity).getItems()
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all, null)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById(R.id.allRv)
        mRecyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView!!.adapter=adapter(items)
    }
}