package jp.yitt.filter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import java.util.*

class SearchConditionListAdapter(val searchConditions: ArrayList<String>) :
        RecyclerView.Adapter<SearchConditionListAdapter.ViewHolder>() {

    var listener: OnSearchConditionClickListener? = null

    override fun getItemCount(): Int = searchConditions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textCondition.text = searchConditions.get(position)

        if (listener != null) {
            holder.layoutRoot.setOnClickListener { listener!!.onSearchConditionClick(position) };
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_search_condition, parent, false))

    fun setOnSearchConditionClickListener(listener: OnSearchConditionClickListener) {
        this.listener = listener
    }

    interface OnSearchConditionClickListener {
        fun onSearchConditionClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textCondition = view.findViewById(R.id.text_condition) as TextView
        val layoutRoot = view.findViewById(R.id.layout_root) as FrameLayout
    }
}