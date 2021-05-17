package com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.rest.RestMain.SikdangSetting.AddMenuIngRVAdapter
import com.example.sikdangbook_rest.Table.TableData_res
import com.example.sikdangbook_rest.Table.Table_res

//SikdangSettingDialog 에서 사용
//테이블 층수 선택 / 테이블 층 추가 가능
class TableFloorSettingDialog(context: Context, val sikdangNum: String): Dialog(context) {

    lateinit var floorListRV:RecyclerView
    lateinit var RVAdapter:FloorListRVAdapter
    lateinit var tableData:TableData_res
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_tablefloorsetting_dialog)
        getTableData()


        floorListRV = findViewById(R.id.floorListRV)


        RVAdapter = FloorListRVAdapter(context, this)
        floorListRV.adapter = RVAdapter

        var LM2 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        floorListRV.layoutManager=LM2
        floorListRV.setHasFixedSize(true)

        var tfs_addFloorBtn: Button = findViewById(R.id.tfs_addFloorBtn)
        tfs_addFloorBtn.setOnClickListener {
            addFloor()
        }

        var tfs_closeBtn:Button = findViewById(R.id.tfs_closeBtn)
        tfs_closeBtn.setOnClickListener { this.dismiss() }

    }
    //데이터베이스 접속
    private fun getTableData(){
        //테이블정보만 가져오면 됨
        tableData = TableData_res("0900")
    }


    //floor은 몇 층인지 직접적으로 알려줌
    //floorNum은 이 층이 신당이 존재하는 층들 중 몇 번째인지
    //식당이 5, 6, 7, 8층이면
    //5층일 때 floor 은 5 floorNum 은 0
    //6층일 때 floor 은 6 floorNum은 1
    //일단 TableData_res의 구조때문에 둘다 받는데 층만으로 데이터 받아올 수 있으면 굳이 FloorNum 사용할 필요 없음

    public fun showTableSettingDialog(floorNum:Int, floor:Int){
        var customDialog = TableSettingDialog(context,sikdangNum, floorNum)
        customDialog!!.show()
    }

    private fun addFloor(){
        //새 층은 현재 최대 층보다 1층 추가
        var newFloor = tableData.floorList[tableData.floorList.size-1]+1
        //데이터베이스로 층 전송하고 그 층에 임의의 테이블데이터 하나 추가
        //굳이 이 클래스 형태가 되지 않아도 됨 그냥 데이터베이스에 전송만 가능하면 된다.
        var newTable= Table_res(0.1F, 0.1F, 50, 50, 2, newFloor, false, false)
        //데이터 전송후 다시 데이터 불러온다.
        getTableData()
        //리사이클러뷰 갱신
        renewalRV()
    }

    public fun renewalRV(){
        RVAdapter.notifyDataSetChanged()

    }

}