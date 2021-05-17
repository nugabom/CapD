package com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.sikdangbook_rest.Table.TableData_res
import com.example.sikdangbook_rest.Table.Table_res

//FloorListRVAdapter 에서 사용
//층을 받아와 그 층의 테이블 정보 띄우고 테이블 정보 수정
class TableSettingDialog(context: Context, val sikdangNum: String, val floorNum: Int): Dialog(context) {
    lateinit var tableLayout:ConstraintLayout
    lateinit var tableData:TableData_res
    var TPC = 0

    var buttonAL=ArrayList<Button>()

    var changedTableAL = ArrayList<Table_res>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_tablesetting_dialog)


        tableLayout = findViewById(R.id.ts_tableSettingLayout)
        //tableData = TableData_res("09:30")


        getTableData()
        var ts_floorTV = findViewById<TextView>(R.id.ts_floorTV)
        ts_floorTV.setText(tableData.floorList[floorNum].toString()+"층")

        //initalTableSet()

        setTableAL()
        setTable()



        //변경버튼 클릭시
        //테이블 정보 새로 저장
        var ts_changeBtn:Button = findViewById(R.id.ts_changeBtn)
        ts_changeBtn.setOnClickListener {

            setNowLoc()
            //setTable()

            //Log.d("확인 변경좌표", "\n\n\n")

            /*
            for (i in 0..changedTableAL.size-1){
                tableLayout.removeView(buttonAL[i])
            }
            buttonAL=ArrayList<Button>()*/

            tableDataSet()

        }


        var  ts_addRectTableBtn:Button = findViewById(R.id.ts_addRectTableBtn)
        ts_addRectTableBtn.setOnClickListener {
            addTable(false)
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
        //변경 테이블리스트 비우고 새로 집어넣음
        //Log.d("확인 setTable", "0")
        changedTableAL = ArrayList<Table_res>()
        var i = 0
        //Log.d("확인 setTable", "1")
        while(i<tableData.tableNumList[floorNum]) {
            //Log.d("확인 setTable", "2 "+i.toString())
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
        //Log.d("확인 setTable", "3")
    }

    //현재 테이블들의 위치를 저장
    fun setNowLoc(){
        for (i in 0..changedTableAL.size-1){
            var count = i
            var originWidth = pxTodp(tableLayout.width.toFloat())
            var originHeight = pxTodp(tableLayout.height.toFloat())

            var width = originWidth - tableData.tableList[count].lengX
            var height = originHeight - tableData.tableList[count].lengY

            changedTableAL[i].locX= pxTodp(buttonAL[i].x)/width
            changedTableAL[i].locy=pxTodp(buttonAL[i].y)/height

            Log.d("확인 변경좌표", i.toString()+" "+changedTableAL[i].locX.toString() + "  " + changedTableAL[i].locy.toString())
            Log.d("확인 원본크기", i.toString()+" "+originWidth.toString() + "  " + width.toString())
        }
    }

    public fun setTable(){
        //현재 테이블 모두 삭제하고 changedTableAL 에 따라 테이블 재배치

        Log.d("확인 setTable", "작동")
        for ( i in 0..buttonAL.size-1){
            tableLayout.removeView(buttonAL[i])
        }
        buttonAL=ArrayList<Button>()

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

            var layoutParams = ConstraintLayout.LayoutParams(dpToPx(changedTableAL[count].lengX), dpToPx(changedTableAL[count].lengY))
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

            button.setOnClickListener {
                Log.d("확인 버튼클릭", "@@@@@@@@@@@@@@@@@@@@@@@@")

            }
            button.setOnTouchListener{ v, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        moveX = v.x - event.rawX
                        moveY = v.y - event.rawY
                        var c = button.x
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


            buttonAL.add(button)


            i++

        }



    }

    public fun addTable(isCircle:Boolean){

        setNowLoc()
        var a =Table_res(0.0f, 0.0f, 30, 30, 1, tableData.floorList[floorNum], true, isCircle)
        Log.d("확인 tableAL 크기 확인1: ", changedTableAL.size.toString())
        //changedTableAL.add(Table_res(0.0f, 0.0f, 30, 30, 1, tableData.floorList[floorNum], true, isCircle))

        changedTableAL.add(a)
        Log.d("확인 tableAL 크기 확인2: ", changedTableAL.size.toString())
        setTable()
    }



    private fun getTableData(){
        tableData = TableData_res("09:30")
    }

    private fun tableDataSet(){
        // changedTableAL 을 데이터베이스로 넘긴다
        // 데이터베이스에 이 층에 속해있는 데이터를 이걸로 대체
        //floorNum 사용하거나 이전 다이얼로그에서 floor 받아와서 층 정보 넘긴다.

        //이후 데이터 다시 불러옴
        getTableData()
        setTableAL()
        setTable()
    }

    inner class Loc(locX:Float, locY:Float, width:Float, height:Float)

    public fun showOneTableSettingDialog(){

    }
}