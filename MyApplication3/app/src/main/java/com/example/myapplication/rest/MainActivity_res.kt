package com.example.sikdangbook_rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R
import com.example.sikdangbook_rest.Table.TableData_res


class MainActivity_res : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_activity_main)
        var startButton = findViewById<Button>(R.id.button_start)
        startButton.setOnClickListener {
            val intent = Intent(this, ChoiceSikdangPage_res::class.java)
            startActivity(intent)


        }
    }
}