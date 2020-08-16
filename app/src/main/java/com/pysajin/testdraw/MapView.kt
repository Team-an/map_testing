package com.pysajin.testdraw

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class MapView(context: Context, attrs: AttributeSet?, defStyle: Int) : View(context, attrs, defStyle) {


    private var mImage: Drawable? = null
    private var mPosX: Float = 0.toFloat()
    private var mPosY: Float = 0.toFloat()

    private var mLastTouchX: Float = 0.toFloat()
    private var mLastTouchY: Float = 0.toFloat()
    private var mActivePointerId = INVALID_POINTER_ID

    private val mScaleDetector: ScaleGestureDetector
    private var mScaleFactor = 1f

    constructor(context: Context) : this(context, null, 0) {

        Glide.with(context).asBitmap().load(R.drawable.map).into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                mImage = BitmapDrawable(resources, resource)
                mImage!!.setBounds(0, 0, mImage!!.intrinsicWidth, mImage!!.intrinsicHeight)
            }
        })
//        mImage = resources.getDrawable(R.drawable.pattern8)
//        mImage!!.setBounds(0, 0, mImage!!.intrinsicWidth, mImage!!.intrinsicHeight)
    }

    // called when XML tries to inflate this View
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {

        Glide.with(context).asBitmap().load(R.drawable.map).into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                mImage = BitmapDrawable(resources, resource)
                mImage!!.setBounds(0, 0, mImage!!.intrinsicWidth, mImage!!.intrinsicHeight)
            }
        })



//        mImage = resources.getDrawable(R.drawable.map)
//        mImage!!.setBounds(0, 0, mImage!!.intrinsicWidth, mImage!!.intrinsicHeight)
    }

    init {
        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev)

        val action = ev.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val x = ev.x
                val y = ev.y

                mLastTouchX = x
                mLastTouchY = y
                mActivePointerId = ev.getPointerId(0)
            }

            MotionEvent.ACTION_MOVE -> {
                val pointerIndex = ev.findPointerIndex(mActivePointerId)
                val x = ev.getX(pointerIndex)
                val y = ev.getY(pointerIndex)

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector.isInProgress) {
                    val dx = x - mLastTouchX
                    val dy = y - mLastTouchY

                    mPosX += dx
                    mPosY += dy

                    invalidate()
                }

                mLastTouchX = x
                mLastTouchY = y
            }

            MotionEvent.ACTION_UP -> {
                mActivePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_CANCEL -> {
                mActivePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex =
                    ev.action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
                val pointerId = ev.getPointerId(pointerIndex)
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    mLastTouchX = ev.getX(newPointerIndex)
                    mLastTouchY = ev.getY(newPointerIndex)
                    mActivePointerId = ev.getPointerId(newPointerIndex)
                }
            }
        }

        return true
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(mImage == null) return

        canvas.save()
        Log.d("MapView", "X: $mPosX Y: $mPosY")
        canvas.translate(mPosX, mPosY)
        canvas.scale(mScaleFactor, mScaleFactor)
        mImage!!.draw(canvas)
        canvas.restore()
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            mScaleFactor *= detector.scaleFactor

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f))

            invalidate()
            return true
        }
    }

    companion object {

        private val INVALID_POINTER_ID = -1
    }

}