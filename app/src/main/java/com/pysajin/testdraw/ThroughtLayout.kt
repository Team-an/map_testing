package com.pysajin.testdraw

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout

class ThroughtLayout(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        for (i in 0 until this.childCount) {
            this.getChildAt(i).dispatchTouchEvent(ev)
        }
        return true
    }
}