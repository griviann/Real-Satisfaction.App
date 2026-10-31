package com.ncorti.kotlin.template.app

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.TextView

class FloatingService : Service() {
    private var windowManager: WindowManager? = null
    private var bubble: TextView? = null
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        bubble = TextView(this).apply{
            text = "RS\n❤️"
            textSize = 20f
            setPadding(40,40,40,40)
            setBackgroundColor(0xFFAA336A.toInt())
            setTextColor(0xFFFFFFFF.toInt())
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply{ gravity = Gravity.TOP or Gravity.START; x=100; y=300 }
        bubble?.setOnTouchListener(object: android.view.View.OnTouchListener{
            var initialX=0; var initialY=0; var initialTouchX=0f; var initialTouchY=0f
            override fun onTouch(v: android.view.View?, e: MotionEvent): Boolean {
                when(e.action){
                    MotionEvent.ACTION_DOWN->{ initialX=params.x; initialY=params.y; initialTouchX=e.rawX; initialTouchY=e.rawY; return true }
                    MotionEvent.ACTION_MOVE->{ params.x=initialX+(e.rawX-initialTouchX).toInt(); params.y=initialY+(e.rawY-initialTouchY).toInt(); windowManager?.updateViewLayout(bubble, params); return true }
                }
                return false
            }
        })
        windowManager?.addView(bubble, params)
    }
    override fun onDestroy(){ super.onDestroy(); bubble?.let{ windowManager?.removeView(it) } }
}
