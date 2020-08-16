package com.pysajin.testdraw

import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.scale_activity.*

class MainActivity :AppCompatActivity() {
//    private lateinit var drawView: MyDrawView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        drawView = drawing
//
//    }
//
//    fun paintClicked(view: View){
//        //set pattern
//        val pattern = view.tag.toString()
//        Log.e("ERRR","hehe $pattern")
//        drawView.setPattern(pattern)
//    }


    private lateinit var drawView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scale_activity)
        drawView = mapView1

    }
//
//    fun paintClicked(view: View){
//        //set pattern
//        val pattern = view.tag.toString()
//        Log.e("ERRR","hehe $pattern")
//        drawView.setPattern(pattern)
//    }



}