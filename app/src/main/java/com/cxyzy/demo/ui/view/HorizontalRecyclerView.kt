package com.cxyzy.demo.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class HorizontalRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    init {
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    }

    private var mDownX: Int = 0
    private var mDownY: Int = 0

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = ev.x.toInt()
                mDownY = ev.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = ev.x.toInt()
                val moveY = ev.y.toInt()
                if (abs(moveX - mDownX) > abs(moveY - mDownY)) {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                mDownX = moveX
                mDownY = moveY
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}

