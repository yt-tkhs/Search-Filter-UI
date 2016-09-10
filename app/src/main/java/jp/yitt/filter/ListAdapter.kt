package jp.yitt.filter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListAdapter(private val size: Int) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_work, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = String.format("item %d", position)
        holder.card.setOnClickListener { listener?.onItemClick(position) }
    }

    override fun getItemCount(): Int = this.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text = view.findViewById(R.id.text) as TextView
        val card = view.findViewById(R.id.card_work) as CardView
    }
}
