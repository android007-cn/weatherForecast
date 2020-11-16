package com.cxyzy.weather.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs

/**
 * 用于解决SwipeRefreshLayout内嵌ViewPager时，出现事件冲突，导致ViewPager不能流畅切换页签的问题。
 */
class VpSwipeRefreshLayout(context: Context, attrs: AttributeSet) :
    SwipeRefreshLayout(context, attrs) {

    private var startY: Float = 0.toFloat()
    private var startX: Float = 0.toFloat()
    /**
     *  记录viewPager是否正处于拖拽的标记
     */
    private var mIsVpInDrag: Boolean = false
    private val mTouchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                // 记录手指按下的位置
                startY = ev.y
                startX = ev.x
                // 初始化标记
                mIsVpInDrag = false
            }
            MotionEvent.ACTION_MOVE -> {
                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsVpInDrag) {
                    return false
                }

                // 获取当前手指位置
                val endY = ev.y
                val endX = ev.x
                val distanceX = abs(endX - startX)
                val distanceY = abs(endY - startY)
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsVpInDrag = true
                    return false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                // 初始化标记
                mIsVpInDrag = false
        }
        // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
        return super.onInterceptTouchEvent(ev)
    }
}