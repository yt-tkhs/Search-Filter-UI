package jp.yitt.filter

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View

class WrapContentViewPager : ViewPager {

    private var mCurrentPagePosition = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        try {
            val child = getChildAt(mCurrentPagePosition)
            if (child != null) {
                child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                val h = child.measuredHeight
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun reMeasureCurrentPage(position: Int) {
        mCurrentPagePosition = position
        requestLayout()
    }
}