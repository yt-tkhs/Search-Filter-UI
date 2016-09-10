package jp.yitt.filter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

class SearchConditionItemFragment : Fragment() {

    companion object {
        val KEY_SEARCH_CONDITIONS = "search_conditions"

        fun newInstance(searchConditions: List<String>): SearchConditionItemFragment {
            val fragment = SearchConditionItemFragment()

            val arguments = Bundle()
            arguments.putStringArrayList(KEY_SEARCH_CONDITIONS, ArrayList<String>(searchConditions))
            fragment.arguments = arguments

            return fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.item_fragment_search_condition, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById(R.id.recycler_search_condition) as RecyclerView

        val searchConditions = arguments.getStringArrayList(KEY_SEARCH_CONDITIONS)
        val adapter = SearchConditionListAdapter(searchConditions)

        adapter.setOnSearchConditionClickListener(
                object : SearchConditionListAdapter.OnSearchConditionClickListener {
                    override fun onSearchConditionClick(position: Int) {
                        Log.d("SearchConditionItemFragment", searchConditions.get(position))
                    }
                })

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
    }
}