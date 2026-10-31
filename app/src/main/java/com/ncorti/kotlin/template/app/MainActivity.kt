package com.ncorti.kotlin.template.app

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply{
            orientation = LinearLayout.VERTICAL
            setPadding(60,200,60,60)
        }

        val btnPermission = Button(this).apply{ 
            text = "1. Allow Overlay Permission" 
        }
        val btnStart = Button(this).apply{ 
            text = "2. Start Real Satisfaction Bubble" 
        }

        btnPermission.setOnClickListener{
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }
        btnStart.setOnClickListener{
            startService(Intent(this, FloatingService::class.java))
        }

        layout.addView(btnPermission, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply{bottomMargin=50})
        layout.addView(btnStart, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        
        setContentView(layout)
    }
}
