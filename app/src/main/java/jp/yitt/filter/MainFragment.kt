package jp.yitt.filter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout

class MainFragment : Fragment() {

    val handler = Handler()

    companion object {
        val MARGIN_DP: Int = 12

        fun newInstance() = MainFragment()
    }

    val searchConditionsList: MutableList<List<String>> = mutableListOf()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutMainContent = view.findViewById(R.id.layout_main_content) as FrameLayout
        val recycler = view.findViewById(R.id.recycler_texts) as RecyclerView
        val tabLayout = view.findViewById(R.id.tab_search_condition) as TabLayout
        val pager = view.findViewById(R.id.pager_search_condition) as WrapContentViewPager
        val layoutBottomSheet = view.findViewById(R.id.bottom_sheet) as LinearLayout
        val bottomSheet = BottomSheetBehavior.from(layoutBottomSheet)
        val filterButton = view.findViewById(R.id.image_button_filter) as ImageButton
        val swipeRefreshLayout = view.findViewById(R.id.layout_swipe_refresh) as SwipeRefreshLayout

        swipeRefreshLayout.setEnabled(false)

        val sortingCondition = resources.getStringArray(R.array.sort_type).toList()
        val filteringCondition = resources.getStringArray(R.array.filter_type).toList()
        searchConditionsList.add(sortingCondition)
        searchConditionsList.add(filteringCondition)

        val marginPx = (MARGIN_DP * resources.displayMetrics.density + 0.5f).toInt()
        recycler.addItemDecoration(TopBottomMarginItemDecoration(marginPx, marginPx))

        val layoutManager = object : LinearLayoutManager(activity) {
            override fun canScrollVertically()
                    = bottomSheet.state == BottomSheetBehavior.STATE_COLLAPSED
        }
        recycler.layoutManager = layoutManager
        recycler.adapter = ListAdapter(100)

        pager.adapter = SearchConditionFragmentManager(childFragmentManager, searchConditionsList)

        tabLayout.setupWithViewPager(pager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            }
        })

        var initialOffset = -1
        var currentItem = -1
        var shouldUpdate = true

        bottomSheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (shouldUpdate) {
                    currentItem = layoutManager.findFirstVisibleItemPosition()
                    initialOffset = layoutManager.getChildAt(0).top
                    shouldUpdate = false
                }

                if (slideOffset >= 0.0f && slideOffset <= 1.0f) {
                    layoutMainContent.foreground = ColorDrawable(Color.argb((128 * slideOffset).toInt(), 0, 0, 0))

                    val scale: Float = 1.0f - (slideOffset / 10.0f)
                    recycler.scaleX = scale
                    recycler.scaleY = scale

                    filterButton.rotation = 360.0f * slideOffset
                    val buttonScale = Math.abs(slideOffset - 0.5f) * 2
                    filterButton.scaleX = buttonScale
                    filterButton.scaleY = buttonScale

                    if (slideOffset >= 0.5f) {
                        filterButton.setImageDrawable(ContextCompat
                                .getDrawable(context, R.drawable.ic_close_grey_600_24dp))
                    } else {
                        filterButton.setImageDrawable(ContextCompat
                                .getDrawable(context, R.drawable.ic_filter_list_grey_600_24dp))
                    }

                    val offset = layoutMainContent.measuredHeight.toFloat() * (slideOffset / 10f)

                    recycler.translationY = -offset
                    recycler.setPadding(0, offset.toInt(), 0, 0)
                    layoutManager.scrollToPositionWithOffset(currentItem, initialOffset)
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        shouldUpdate = true

                        swipeRefreshLayout.isRefreshing = true
                        handler.postDelayed(object : Runnable {
                            override fun run() {
                                swipeRefreshLayout.isRefreshing = false
                                handler.removeCallbacks(this)
                            }
                        }, 2000)
                    }
                }
            }
        })

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP
                    && bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED) {

                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        filterButton.setOnClickListener { view ->
            if (bottomSheet.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            } else if (bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}