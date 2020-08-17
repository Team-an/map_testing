package com.pysajin.testdraw

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :AppCompatActivity() {
    private lateinit var drawView: MyDrawView
    private lateinit var mapViews: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawView = drawing
        mapViews = mapView
    }

    fun paintClicked(view: View){
        //set pattern
        val pattern = view.tag.toString()

        if(pattern == "erase"){
            drawView.setTouchBool(true)
            mapViews.setTouchBool(false)
        }
        else{
            drawView.setTouchBool(false)
            mapViews.setTouchBool(true)
        }
        Log.e("ERRR","hehe $pattern")
        drawView.setPattern(pattern)
    }
}