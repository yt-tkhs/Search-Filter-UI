package jp.yitt.filter

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class TopBottomMarginItemDecoration(val topMarginPx: Int, val bottomMarginPx: Int) :
        RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemCount = parent.adapter.itemCount
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.top = topMarginPx
        }

        if (position == itemCount - 1) {
            outRect.bottom = bottomMarginPx
        }
    }
}
