package com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting

import android.app.Dialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.example.myapplication.R
import com.example.sikdangbook_rest.Table.TableData_res
import com.google.android.material.internal.ViewUtils.dpToPx

class TableSettingDialog(context: Context, val sikdangNum: String, val floorNum:Int): Dialog(context) {
    lateinit var tableLayout:ConstraintLayout
    lateinit var tableData:TableData_res

    var buttonAL=ArrayList<Button>()
    var lpAL = ArrayList<ConstraintLayout.LayoutParams>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_tablesetting_dialog)


        tableLayout = findViewById(R.id.ts_tableSettingLayout)
        //tableData = TableData_res("09:30")

        getTableData()


        initalTableSet()








    }


    private fun initalTableSet(){

        var tableLayout:ConstraintLayout = findViewById(R.id.ts_tableSettingLayout)

        //var floorNumTV = findViewById<TextView>(R.id.floorNumTV)
        //floorNumTV.setText(tableData.floorList[floorNum])
        //floorNumTV.setText(tableData.floorList[floorNum].toString()+"층")
        var i = 0
        while(i<tableData.tableNumList[floorNum]){
            //Log.d("확인 TableFloorFragment i", i.toString())
            var count = i
            if (floorNum == 0){
                count = i
            }
            else{
                count = i+tableData.accumTableNumList[floorNum-1]//테이블리스트의 몇번째인가
            }

            //Log.d("확인 TableFloorFragment 카운트", count.toString())
            var button= Button(getContext())
            if (tableData.tableList[count].isCircle == true)//원형테이블
            {
                var roundDrawable = context.getDrawable(R.drawable.button_round_gray)
                button.background = roundDrawable
            }
            else {
                button.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }
            var moveX = 0f
            var moveY = 0f

            var layoutParams = ConstraintLayout.LayoutParams(dpToPx(tableData.tableList[i].lengX), dpToPx(tableData.tableList[i].lengY))
            //with와 height에 픽셀값 들어감 => dp를 픽셀값으로 변환한 값 들어가야 한다.

            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.horizontalBias = tableData.tableList[count].locX//0.5가 중앙
            layoutParams.verticalBias = tableData.tableList[count].locy


            button.setLayoutParams(layoutParams)
            tableLayout.addView(button)

            button.setOnTouchListener{ v, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        moveX = v.x - event.rawX
                        moveY = v.y - event.rawY

                        var a = button.layoutParams.height
                        var b =button.pivotX
                        var c =button.x
                        var d =layoutParams.horizontalBias
                        Log.d("확인 버튼좌표", a.toString()+" "+b.toString()+" "+c.toString()+" "+d.toString())
                    }
                    MotionEvent.ACTION_MOVE -> {
                        v.animate()
                                .x(event.rawX + moveX)
                                .y(event.rawY + moveY)
                                .setDuration(0)
                                .start()
                    }

                }
                true

            }
            button.setOnClickListener {

            }

            lpAL.add(layoutParams)

            buttonAL.add(button)


            

            i++

        }

        var a = buttonAL[0].layoutParams.height
        var b =buttonAL[0].pivotX
        var c =buttonAL[0].x


        Log.d("확인 버튼좌표", a.toString()+" "+b.toString()+" "+c.toString())
    }


    public fun dpToPx(dp:Int):Int {
        var density:Float = context.getResources().getDisplayMetrics().density;
        return Math.round(dp.toFloat() * density.toFloat()).toInt()
    }

    private fun getTableData(){
        tableData = TableData_res("09:30")
    }
}