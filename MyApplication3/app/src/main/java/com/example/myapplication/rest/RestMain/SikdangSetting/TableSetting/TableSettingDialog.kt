package com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.sikdangbook_rest.Table.TableData_res
import com.example.sikdangbook_rest.Table.Table_res


class TableSettingDialog(context: Context, val sikdangNum: String, val floorNum: Int): Dialog(context) {
    lateinit var tableLayout:ConstraintLayout
    lateinit var tableData:TableData_res
    var TPC = 0

    var buttonAL=ArrayList<Button>()

    var changedTableAL = ArrayList<Table_res>()

    var xAL = ArrayList<Float>()

    var locAL = ArrayList<Loc>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_tablesetting_dialog)


        tableLayout = findViewById(R.id.ts_tableSettingLayout)
        //tableData = TableData_res("09:30")


        getTableData()


        //initalTableSet()

        setTableAL()
        setTable()



        //변경버튼 클릭시
        //테이블 정보 새로 저장
        var ts_changeBtn:Button = findViewById(R.id.ts_changeBtn)
        ts_changeBtn.setOnClickListener {



            //Log.d("확인 변경 가로길이", width.toString())


            for (i in 0..changedTableAL.size-1){
                var count = i
                var originWidth = pxTodp(tableLayout.width.toFloat())
                var originHeight = pxTodp(tableLayout.height.toFloat())

                var width = originWidth - tableData.tableList[count].lengX
                var height = originHeight - tableData.tableList[count].lengY
                //Log.d("확인 변경for문시작",buttonAL.size.toString())
                //var c = buttonAL[i].x
                //Log.d("확인 변경for문2",c.toString())
                //var putx=pxTodp(c)/(pxTodp(tableLayout.width.toFloat()))
                changedTableAL[i].locX= pxTodp(buttonAL[i].x)/width
                changedTableAL[i].locy=pxTodp(buttonAL[i].y)/height

                Log.d("확인 변경좌표", i.toString()+" "+changedTableAL[i].locX.toString() + "  " + changedTableAL[i].locy.toString())
            }
            Log.d("확인 변경좌표", "\n\n\n")

            for (i in 0..changedTableAL.size-1){
                tableLayout.removeView(buttonAL[i])
            }
            buttonAL=ArrayList<Button>()

            setTable()

        }


        var ts_cancelBtn:Button = findViewById(R.id.ts_cancelBtn)
        ts_cancelBtn.setOnClickListener { this.dismiss() }


    }




    public fun dpToPx(dp: Int):Int {
        var density:Float = context.getResources().getDisplayMetrics().density;
        return Math.round(dp.toFloat() * density.toFloat())
    }

    public fun pxTodp(px: Float):Float {
        val metrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        val dp: Float = px / (metrics.densityDpi / 160f)
        //Log.d("확인 pxTodp", px.toString()+" "+Math.round(dp).toFloat().toString())
        return Math.round(dp).toFloat()



    }

    public fun setTableAL(){
        var i = 0
        while(i<tableData.tableNumList[floorNum]) {
            //Log.d("확인 TableFloorFragment i", i.toString())
            var count = i
            if (floorNum == 0) {
                count = i
            } else {
                count = i + tableData.accumTableNumList[floorNum - 1]//테이블리스트의 몇번째인가
            }
            //tableData.tableList[count]

            var tempTable=tableData.tableList[count]

            changedTableAL.add(tempTable)

            i++

        }
    }

    public fun setTable(){
        //var floorNumTV = findViewById<TextView>(R.id.floorNumTV)
        //floorNumTV.setText(tableData.floorList[floorNum])
        //floorNumTV.setText(tableData.floorList[floorNum].toString()+"층")

        var i = 0
        while(i< changedTableAL.size){
            var count=i
            var button= Button(getContext())
            if (changedTableAL[i].isCircle == true)//원형테이블
            {
                var roundDrawable = context.getDrawable(R.drawable.button_round_gray)
                button.background = roundDrawable
            }
            else {
                button.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }
            var moveX = 0f
            var moveY = 0f

            //var a = tableLayout.layoutParams

            var layoutParams = ConstraintLayout.LayoutParams(dpToPx(tableData.tableList[i].lengX), dpToPx(tableData.tableList[i].lengY))
            //with와 height에 픽셀값 들어감 => dp를 픽셀값으로 변환한 값 들어가야 한다.


            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.horizontalBias = changedTableAL[i].locX//0.5가 중앙
            layoutParams.verticalBias = changedTableAL[i].locy


            button.setLayoutParams(layoutParams)
            tableLayout.addView(button)

            button.x=changedTableAL[i].locX
            button.y=changedTableAL[i].locy


            //Log.d("확인 변경크기", WIDTH.toString()+" "+width.toString()+" "+height.toString())





            button.setOnTouchListener{ v, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        moveX = v.x - event.rawX
                        moveY = v.y - event.rawY
                        var c = button.x
                        //var putx=pxTodp(c)/(pxTodp(tableLayout.width.toFloat()))
                        //Log.d("확인 버튼터치", i.toString()+" "+changedTableAL.size.toString())

                        //changedTableAL[i].locX= putx
                        //changedTableAL[i].locy=pxTodp(buttonAL[i].y)/(pxTodp(tableLayout.width.toFloat()))
                        var WIDTH = pxTodp(tableLayout.width.toFloat())
                        var HEIGHT = pxTodp(tableLayout.height.toFloat())

                        var width = WIDTH - tableData.tableList[count].lengX
                        var height = HEIGHT - tableData.tableList[count].lengY

                        var a =pxTodp(button.x)/width
                        var b =pxTodp(button.y)/height

                        Log.d("확인 변경좌표", WIDTH.toString()+" "+width.toString()+" "+a.toString()+" "+b.toString())


                    }
                    MotionEvent.ACTION_MOVE -> {
                        v.animate()
                                .x(event.rawX + moveX)
                                .y(event.rawY + moveY)
                                .setDuration(0)
                                .start()
                    }

                }
                false

            }
            button.setOnClickListener {
                Log.d("확인 버튼클릭", "@@@@@@@@@@@@@@@@@@@@@@@@")
            }

            buttonAL.add(button)



            i++

        }






    }


    private fun getTableData(){
        tableData = TableData_res("09:30")
    }

    inner class Loc(locX:Float, locY:Float, width:Float, height:Float)
}