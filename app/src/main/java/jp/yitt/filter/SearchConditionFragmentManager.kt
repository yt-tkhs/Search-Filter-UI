package jp.yitt.filter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SearchConditionFragmentManager(
        fm: FragmentManager, private val searchConditionsList: List<List<String>>) :
        FragmentPagerAdapter(fm) {

    val selectedConditions: Array<Int> = Array(searchConditionsList.size, { i -> 0 })

    override fun getItem(position: Int): Fragment
            = SearchConditionItemFragment.newInstance(searchConditionsList.get(position))

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence
            = searchConditionsList.get(position).get(selectedConditions[position])

    fun selectCondition(conditionIndex: Int, itemIndex: Int) {
        selectedConditions[conditionIndex] = itemIndex
    }
}