package com.pysajin.testdraw

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class MyDrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    //drawing path
    private val paths: ArrayList<Path> = ArrayList()

    private var pathNum: Int = 0

    //drawing and canvas paint
    private val drawPaint: Paint = Paint()
    private var canvasPaint: Paint? = null

    //canvas
    private lateinit var drawCanvas: Canvas
    //canvas bitmap
    private lateinit var canvasBitmap: Bitmap

    var test: String = "pattern1"

    init {
        setupDrawing()
    }


    private fun setupDrawing() {
        drawPaint.isAntiAlias = true
        drawPaint.strokeWidth = 0f
        drawPaint.style = Paint.Style.FILL_AND_STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)

        paths.add(Path())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        if (paths.isEmpty() || pathNum >= paths.size) return
        canvas.drawBitmap(canvasBitmap, 0f, 0f, canvasPaint)
        canvas.drawPath(paths[pathNum], drawPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX: Float = event.x
        val touchY: Float = event.y

        if (test != "erase") {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    paths.add(Path())
                    paths[pathNum].moveTo(touchX, touchY)
                }

                MotionEvent.ACTION_MOVE ->
                    paths[pathNum].lineTo(touchX, touchY)


                MotionEvent.ACTION_UP -> {
                    paths[pathNum].lineTo(touchX, touchY)
                    drawCanvas.drawPath(paths[pathNum], drawPaint)
                    paths[pathNum].close()
//                    paths[pathNum].reset()
                    pathNum++
                }
                else ->
                    return false
            }

            invalidate()
            return true
        } else {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    for (p: Path in paths) {
                        val pBounds = RectF()
                        p.computeBounds(pBounds, true)

                        if (pBounds.contains(touchX, touchY)) {
                            //select path
                            Log.e("ERRRR", "hihi2222 $touchX $touchY")
                            drawCanvas.drawPath(p, drawPaint)

                            break;
                        }
                    }
                    invalidate()
                }
                else -> return false
            }
        }
        return true
    }

    fun setPattern(newPattern: String) {
        test = newPattern
        invalidate()
        val patternID = resources.getIdentifier(newPattern, "drawable", "com.pysajin.testdraw")
        val patternBMP = BitmapFactory.decodeResource(resources, patternID)
        val patternBMPshader = BitmapShader(patternBMP, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)

        drawPaint.shader = patternBMPshader
    }


}